package com.adamkis.pizza.ui.view

import android.content.Context
import android.util.AttributeSet
import com.adamkis.pizza.R

class CheckoutView : WideButtonView {


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
        setColor(WideButtonView.Color.RED)
        hideIcon()
        setMainText(R.string.checkout)
    }

    fun showBackToHome(){
        setMainText(R.string.back_to_home)
        hidePrice()
    }

}