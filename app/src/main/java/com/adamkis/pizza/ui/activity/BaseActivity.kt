package com.adamkis.pizza.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.adamkis.pizza.R
import com.adamkis.pizza.model.Ingredient
import com.adamkis.pizza.model.Pizza
import com.adamkis.pizza.ui.fragment.PizzaDetailFragment

/**
 * Created by adam on 2018. 01. 11..
 */
abstract class BaseActivity : AppCompatActivity(){

    protected fun setupToolbar(@IdRes toolbarId: Int){
        val toolbar = findViewById<Toolbar>(toolbarId) as Toolbar
        setSupportActionBar(toolbar)
    }

    protected fun setupBackButton(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}