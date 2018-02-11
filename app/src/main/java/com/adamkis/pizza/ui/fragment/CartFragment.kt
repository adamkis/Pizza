package com.adamkis.pizza.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.adamkis.pizza.App
import com.adamkis.pizza.R
import com.adamkis.pizza.helper.FilePersistenceHelper
import com.adamkis.pizza.helper.logDebug
import com.adamkis.pizza.helper.logThrowable
import com.adamkis.pizza.model.Cart
import com.adamkis.pizza.network.RestApi
import com.adamkis.pizza.ui.adapter.CartAdapter
import io.paperdb.Paper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cart.*
import javax.inject.Inject

/**
 * Created by adam on 2018. 01. 11..
 */
class CartFragment : BaseFragment() {

    private var cart: Cart? = null
    lateinit var cartRecyclerView: RecyclerView

    @Inject lateinit var restApi: RestApi
    private var callDisposable: Disposable? = null
    private var clickDisposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        App.netComponent.inject(this)
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartRecyclerView = view.findViewById<RecyclerView>(R.id.cart_recycler_view)
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
//                .doOnSubscribe { showLoading(true) }
//                .doAfterTerminate { showLoading(false) }
                .subscribe(
                        {response ->
                            logDebug("Response to Order")
                            logDebug(response.string())
                        },
                        {t ->
//                            when(t){
//                                is UnknownHostException -> {
//                                    showError(getString(R.string.network_error))
//                                }
//                                is NullPointerException -> {
//                                    showError(getString(R.string.could_not_load_data))
//                                }
//                                else -> {
//                                    showError(getString(R.string.error))
//                                }
//                            }
                            logDebug("ERROR when sending JSON")
                            logThrowable(t)
                        }
                )
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

    companion object {
        fun newInstance(): CartFragment {
            return CartFragment()
        }
    }

    override fun onDestroy() {
        callDisposable?.dispose()
        clickDisposable?.dispose()
        super.onDestroy()
    }

}