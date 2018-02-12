package com.adamkis.pizza.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.adamkis.pizza.App
import com.adamkis.pizza.R
import com.adamkis.pizza.helper.FilePersistenceHelper
import com.adamkis.pizza.model.Cart
import com.adamkis.pizza.model.Drink
import com.adamkis.pizza.network.RestApi
import com.adamkis.pizza.ui.adapter.DrinksAdapter
import io.paperdb.Paper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_drinks.*
import java.net.UnknownHostException
import javax.inject.Inject

class DrinksFragment : BaseFragment() {

    private var cart: Cart? = null

    @Inject lateinit var restApi: RestApi
    private var callDisposable: Disposable? = null
    private var clickDisposable: Disposable? = null

    companion object {
        fun newInstance(): DrinksFragment {
            return DrinksFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        App.netComponent.inject(this)
        return inflater.inflate(R.layout.fragment_drinks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val drinksRecyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.drinks_recycler_view)
        setUpLoadingAndError(view.findViewById(R.id.loading), view as CoordinatorLayout)
        added_to_cart.hide()
        cart = Paper.book().read(FilePersistenceHelper.PAPER_CART_KEY, Cart())
        if (cart == null) {
            showError(getString(R.string.error))
            showLoading(false)
        }
        else{
            downloadData(drinksRecyclerView)
        }
    }


    private fun downloadData(drinksRecyclerView: RecyclerView){
        callDisposable = restApi.getDrinks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading(true) }
            .doAfterTerminate { showLoading(false) }
            .subscribe(
                {drinksResponse ->
                    setUpAdapter(drinksRecyclerView, cart!!, drinksResponse)
                },
                {t ->
                    when(t){
                        is UnknownHostException -> {
                            showError(getString(R.string.network_error))
                        }
                        is NullPointerException -> {
                            showError(getString(R.string.could_not_load_data))
                        }
                        else -> {
                            showError(getString(R.string.error))
                        }
                    }
                }
            )
    }



    private fun setUpAdapter(drinksRecyclerView: RecyclerView, cart: Cart, drinksResponse: Array<Drink>){
        drinksRecyclerView.layoutManager = LinearLayoutManager(this@DrinksFragment.activity as Context, LinearLayout.VERTICAL, false)
        drinksRecyclerView.adapter = DrinksAdapter(cart, drinksResponse, activity as Context)
        clickDisposable = (drinksRecyclerView.adapter as DrinksAdapter).clickEvent
                .subscribe({ added_to_cart.showFlash() })
    }

    override fun onPause() {
        Paper.book().write(FilePersistenceHelper.PAPER_CART_KEY, cart)
        super.onPause()
    }

    override fun onDestroy() {
        clickDisposable?.dispose()
        callDisposable?.dispose()
        super.onDestroy()
    }


}