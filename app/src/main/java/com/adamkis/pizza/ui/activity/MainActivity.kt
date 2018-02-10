package com.adamkis.pizza.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.adamkis.pizza.R
import com.adamkis.pizza.helper.FilePersistenceHelper
import com.adamkis.pizza.helper.logDebug
import com.adamkis.pizza.model.Cart
import com.adamkis.pizza.ui.fragment.PizzasFragment
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private var activeFragment: Fragment? = null
    private val ACTIVE_FRAGMENT_KEY = "ACTIVE_FRAGMENT_KEY"

    private var cart: Cart?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(
            if(savedInstanceState != null) supportFragmentManager.getFragment(savedInstanceState, ACTIVE_FRAGMENT_KEY)
            else PizzasFragment.newInstance()
        )
        cart_button.setOnClickListener {
            startActivity(Intent(this@MainActivity as Context, CartActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        updateCart(loadCart())
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, ACTIVE_FRAGMENT_KEY, activeFragment);
    }

    private fun replaceFragment(fragment: Fragment){
        activeFragment = fragment
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, activeFragment).commit()
    }

    private fun loadCart(): Cart {
        return Paper.book().read(FilePersistenceHelper.PAPER_CART_KEY, Cart())
    }

    fun updateCart(cart: Cart){
        logDebug("Cart updated" + cart.toString())
        cart_item_count.text = cart.getItemCount().toString()
    }


}

// TODO check why app size is big
// TODO refactor toolbars
// TODO make drink and ingredient inherit from new abstract class Product
// TODO Put Paper into RXJava
// TODO reuse bottom bar button
// TODO reuse iingredient, pizza, drinks elements
// TODO modify created by...
// TODO No pizza image  - handle it
// TODO MVVM
// TODO handle error in CartFragment