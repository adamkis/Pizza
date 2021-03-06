package com.adamkis.pizza.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import com.adamkis.pizza.App
import com.adamkis.pizza.R
import com.adamkis.pizza.helper.FilePersistenceHelper
import com.adamkis.pizza.helper.logDebug
import com.adamkis.pizza.helper.logThrowable
import com.adamkis.pizza.model.Cart
import com.adamkis.pizza.network.RestApi
import com.adamkis.pizza.ui.activity.BaseActivity
import com.adamkis.pizza.ui.activity.DrinksActivity
import com.adamkis.pizza.ui.adapter.CartAdapter
import io.paperdb.Paper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.toolbar_cart.*
import java.net.UnknownHostException
import javax.inject.Inject

class CartFragment : BaseFragment() {

    private var cart: Cart? = null
    lateinit var cartRecyclerView: RecyclerView

    @Inject lateinit var restApi: RestApi
    private var callDisposable: Disposable? = null
    private var clickDisposable: Disposable? = null

    companion object {
        fun newInstance(): CartFragment {
            return CartFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        App.netComponent.inject(this)
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLoadingAndError(view.findViewById(R.id.loading), view as CoordinatorLayout)
        showLoading(false)
        cartRecyclerView = view.findViewById<RecyclerView>(R.id.cart_recycler_view)

        drinks_button.setOnClickListener {
            startActivity(Intent(activity, DrinksActivity::class.java))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as BaseActivity).setupToolbar(R.id.toolbar)
        (activity as BaseActivity).setupBackButton()
    }

    override fun onResume() {
        super.onResume()
        cart = Paper.book().read(FilePersistenceHelper.PAPER_CART_KEY, Cart())
        cart?.let { setUpAdapter(cartRecyclerView, it) }

        checkout_button.setPriceText(cart?.getTotalPrice())
        checkout_button.setOnClickListener {
            cart?.let { sendOrder(it.getOrder()) }
        }
    }

    private fun sendOrder(order: Cart.Order){
        callDisposable = restApi.order(order)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading(true) }
            .doAfterTerminate { showLoading(false) }
            .subscribe(
                {response ->
                    logDebug(response.string())
                    showFinishScreen()
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
                    logThrowable(t)
                }
            )
    }

    private fun showFinishScreen(){
        checkout_button.showBackToHome()
        checkout_button.setOnClickListener {
            this@CartFragment.activity?.finish()
        }
        thank_you_for_your_order.visibility = VISIBLE
        cart = Cart()
    }

    private fun setUpAdapter(cartRecyclerView: RecyclerView, cart: Cart){
        cartRecyclerView.layoutManager = LinearLayoutManager(activity as Context, LinearLayout.VERTICAL, false)
        cartRecyclerView.adapter = CartAdapter(cart, activity as Context)
        clickDisposable = (cartRecyclerView.adapter as CartAdapter).clickEvent
                .subscribe({ checkout_button.setPriceText(it) })
    }

    override fun onPause() {
        Paper.book().write(FilePersistenceHelper.PAPER_CART_KEY, cart)
        super.onPause()
    }

    override fun onDestroy() {
        callDisposable?.dispose()
        clickDisposable?.dispose()
        super.onDestroy()
    }

}