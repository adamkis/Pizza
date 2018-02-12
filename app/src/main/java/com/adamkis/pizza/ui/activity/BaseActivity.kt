package com.adamkis.pizza.ui.activity

import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

abstract class BaseActivity : AppCompatActivity(){

    fun setupToolbar(@IdRes toolbarId: Int){
        val toolbar = findViewById<Toolbar>(toolbarId) as Toolbar
        setSupportActionBar(toolbar)
    }

    fun setupBackButton(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}