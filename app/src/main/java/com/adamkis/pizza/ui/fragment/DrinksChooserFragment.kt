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
import com.adamkis.pizza.ui.adapter.CartAdapter
import io.paperdb.Paper

/**
 * Created by adam on 2018. 01. 11..
 */
class DrinksChooserFragment : Fragment() {

    private var cart: Cart? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_drinks_chooser, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val drinksRecyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.drinks_recycler_view)

        cart = Paper.book().read(FilePersistenceHelper.PAPER_CART_KEY, Cart())
        cart?.let {
            setUpAdapter(drinksRecyclerView, it)
        }
//        checkout_button.setOnClickListener {
//            logDebug("cart: " + cart)
//        }
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

}