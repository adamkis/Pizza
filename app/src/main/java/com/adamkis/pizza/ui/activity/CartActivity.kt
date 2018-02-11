package com.adamkis.pizza.ui.activity

import android.content.Intent
import android.os.Bundle
import com.adamkis.pizza.R
import com.adamkis.pizza.ui.fragment.CartFragment
import kotlinx.android.synthetic.main.activity_cart.*

/**
 * Created by adam on 2018. 01. 11..
 */
class CartActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, CartFragment.newInstance()).commit()
    }

}