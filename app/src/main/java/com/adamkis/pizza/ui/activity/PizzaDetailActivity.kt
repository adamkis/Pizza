package com.adamkis.pizza.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.adamkis.pizza.R
import com.adamkis.pizza.helper.FilePersistenceHelper
import com.adamkis.pizza.model.Pizza
import com.adamkis.pizza.ui.fragment.PizzaDetailFragment
import kotlinx.android.synthetic.main.activity_pizza_detail.*


/**
 * Created by adam on 2018. 01. 11..
 */
class PizzaDetailActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_detail)

        val pizza: Pizza = intent.getParcelableExtra(Pizza.TAG)

//        var collapsingToolbarLayout: CollapsingToolbarLayout? = null
        val toolbar = findViewById<Toolbar>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

//        collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        pizza_name.text = if (pizza.name.isNullOrBlank()) getString(R.string.pizza_detail) else pizza.name

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PizzaDetailFragment.newInstance(pizza)).commit()

    }

    companion object {
        fun getStartIntent(context: Context, pizza: Pizza): Intent {
            return Intent(context, PizzaDetailActivity::class.java)
                    .apply { putExtra(Pizza.TAG, pizza) }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}