package com.adamkis.pizza.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.adamkis.pizza.R
import com.adamkis.pizza.helper.FilePersistenceHelper
import com.adamkis.pizza.helper.logDebug
import com.adamkis.pizza.model.Cart
import com.adamkis.pizza.model.Drink
import com.adamkis.pizza.model.Pizza
import com.adamkis.pizza.ui.adapter.CartAdapter
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_cart.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by adam on 2018. 01. 11..
 */
class CartFragment : BaseFragment() {

    private var cart: Cart? = null
    lateinit var cartRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartRecyclerView = view.findViewById<RecyclerView>(R.id.cart_recycler_view)
    }

    override fun onResume() {
        super.onResume()
        cart = Paper.book().read(FilePersistenceHelper.PAPER_CART_KEY, Cart())
        cart?.let {
            setUpAdapter(cartRecyclerView, it)
        }
        checkout_button.setOnClickListener {
            // TODO Put it into cart object
            var orderJson = JSONObject()
            var pizzas = JSONArray()
            var drinks = JSONArray()
            // TODO null check
            for ( orderItem in cart!!.orderItems ){
                when( orderItem ){
                    is Pizza -> pizzas.put(orderItem.toJSON())
                    // TODO null check
                    is Drink -> drinks.put(orderItem.id!!)
                }
            }
            orderJson.put("pizzas", pizzas)
            orderJson.put("drinks", drinks)
            logDebug("cart: " + orderJson.toString())
        }
    }


    private fun setUpAdapter(cartRecyclerView: RecyclerView, cart: Cart){
        cartRecyclerView.layoutManager = LinearLayoutManager(this@CartFragment.activity as Context, LinearLayout.VERTICAL, false)
        cartRecyclerView.adapter = CartAdapter(cart.orderItems, activity as Context)
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
        fun newInstance(): CartFragment {
            return CartFragment()
        }
    }

}