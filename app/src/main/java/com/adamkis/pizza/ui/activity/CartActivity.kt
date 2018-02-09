package com.adamkis.pizza.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.adamkis.pizza.R
import com.adamkis.pizza.model.Ingredient
import com.adamkis.pizza.model.Pizza
import com.adamkis.pizza.ui.fragment.PizzaDetailFragment

/**
 * Created by adam on 2018. 01. 11..
 */
class CartActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        setupToolbar(R.id.toolbar)
        setupBackButton()

//        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PizzaDetailFragment.newInstance(pizza, ingredientsHM)).commit()

    }

}