package com.adamkis.pizza.ui.activity

import android.os.Bundle
import com.adamkis.pizza.R
import com.adamkis.pizza.ui.fragment.CartFragment

/**
 * Created by adam on 2018. 01. 11..
 */
class DrinksChooserActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinks_chooser)

        setupToolbar(R.id.toolbar)
        setupBackButton()

//        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, CartFragment.newInstance()).commit()

    }

}