package com.adamkis.pizza.ui.activity

import android.os.Bundle
import com.adamkis.pizza.R
import com.adamkis.pizza.ui.fragment.DrinksFragment

class DrinksActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinks)
        setupToolbar(R.id.toolbar)
        setupBackButton()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, DrinksFragment.newInstance()).commit()
        }
    }

}