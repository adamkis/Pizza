package com.adamkis.pizza.ui.view

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import com.adamkis.pizza.R

class AddToCartView : WideButtonView {


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
        setIcon(R.drawable.cart_small)
        setMainText(R.string.add_to_cart)
        setColor(WideButtonView.Color.ORANGE)
    }

    fun showAddedToCartFlash(){
        showAddedItemToCart()
        setClickableWB(false)
        Handler().postDelayed({
            setMainText(R.string.add_to_cart)
            setColor(WideButtonView.Color.ORANGE)
            showPrice()
            showIcon()
            setClickableWB(true)
        }, 1000)
    }

}