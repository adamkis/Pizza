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
import com.adamkis.pizza.model.Cart
import com.adamkis.pizza.ui.adapter.CartAdapter
import io.paperdb.Paper

/**
 * Created by adam on 2018. 01. 11..
 */
class CartFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cartRecyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.cart_recycler_view)

        var cart: Cart = Paper.book().read(FilePersistenceHelper.PAPER_CART_KEY, Cart())
        setUpAdapter(cartRecyclerView, cart)

    }


    private fun setUpAdapter(cartRecyclerView: RecyclerView, cart: Cart){
        cartRecyclerView.layoutManager = LinearLayoutManager(this@CartFragment.activity, LinearLayout.VERTICAL, false)
        cartRecyclerView.adapter = CartAdapter(cart.orderItems.toTypedArray(), activity as Context)
//        clickDisposable = (pizzasRecyclerView.adapter as PizzasAdapter).clickEvent
//                .subscribe({
//                    startDetailActivityWithTransition(activity as Activity,
//                            it.second.findViewById(R.id.pizza_image),
//                            it.second.findViewById(R.id.pizza_name),
//                            it.first,
//                            ingredientsHM)
//                })
    }



    companion object {
        fun newInstance(): CartFragment {
            return CartFragment()
        }
    }

}