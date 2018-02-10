package com.adamkis.pizza.ui.fragment

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adamkis.pizza.App
import com.adamkis.pizza.R
import com.adamkis.pizza.helper.FilePersistenceHelper
import com.adamkis.pizza.helper.logDebug
import com.adamkis.pizza.model.Cart
import com.adamkis.pizza.network.RestApi
import io.paperdb.Paper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Created by adam on 2018. 01. 11..
 */
class DrinksChooserFragment : BaseFragment() {

    private var cart: Cart? = null

    @Inject lateinit var restApi: RestApi
    private var callDisposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        App.netComponent.inject(this)
        return inflater.inflate(R.layout.fragment_drinks_chooser, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val drinksRecyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.drinks_recycler_view)
        setUpLoadingAndError(view.findViewById(R.id.loading), view as CoordinatorLayout)

        cart = Paper.book().read(FilePersistenceHelper.PAPER_CART_KEY, Cart())
        cart?.let {
            setUpAdapter(drinksRecyclerView, it)
        }
        downloadData(drinksRecyclerView)
//        checkout_button.setOnClickListener {
//            logDebug("cart: " + cart)
//        }
    }


    private fun downloadData(recentsRecyclerView: RecyclerView){
        callDisposable = restApi.getDrinks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLoading(true) }
                .doAfterTerminate { showLoading(false) }
                .subscribe(
                        {drinksResponse ->
//                            this@DrinksChooserFragment.photosResponse = photosResponse
//                            setUpAdapter(recentsRecyclerView, photosResponse)
                            for (drink in drinksResponse){
                                logDebug(drink.name)
                            }
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



    private fun setUpAdapter(cartRecyclerView: RecyclerView, cart: Cart){

//        cartRecyclerView.layoutManager = LinearLayoutManager(this@DrinksChooserFragment.activity as Context, LinearLayout.VERTICAL, false)
//        cartRecyclerView.adapter = CartAdapter(cart.orderItems, activity as Context)

//        clickDisposable = (pizzasRecyclerView.adapter as PizzasAdapter).clickEvent
//                .subscribe({
//                    startDetailActivityWithTransition(activity as Activity,
//                            it.second.findViewById(R.id.pizza_image),
//                            it.second.findViewById(R.id.pizza_name),
//                            it.first,
//                            ingredientsHM)
//                })
    }

    override fun onPause() {
        Paper.book().write(FilePersistenceHelper.PAPER_CART_KEY, cart)
        super.onPause()
    }


    companion object {
        fun newInstance(): DrinksChooserFragment {
            return DrinksChooserFragment()
        }
    }


    override fun onDestroy() {
        callDisposable?.dispose()
        super.onDestroy()
    }


}