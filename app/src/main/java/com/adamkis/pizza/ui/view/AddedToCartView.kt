package com.adamkis.pizza.ui.view

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.View

class AddedToCartView : WideButtonView {


    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context) : super(context) {
        initView()
    }


    override fun initView() {
        super.initView()
        showAddedItemToCart()
    }

    fun hide(){
        this.visibility = GONE
    }

    private fun show(){
        this.visibility = View.VISIBLE
    }

    fun showFlash(){
        show()
        Handler().postDelayed({
            hide()
        }, 1000)
    }

}