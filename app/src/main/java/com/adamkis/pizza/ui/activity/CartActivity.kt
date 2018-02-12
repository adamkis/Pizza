package com.adamkis.pizza.ui.activity

import android.os.Bundle
import com.adamkis.pizza.R
import com.adamkis.pizza.ui.fragment.CartFragment

class CartActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, CartFragment.newInstance()).commit()
        }
    }

}