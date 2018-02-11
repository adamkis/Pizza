package com.adamkis.pizza.ui.view

import android.content.Context
import android.os.Handler
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.adamkis.pizza.R

/**
 * Created by adam on 2018. 02. 07..
 */
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
        setTextMain(R.string.add_to_cart)
        setColor(WideButtonView.Color.ORANGE)
    }

    fun showAddedToCartFlash(){
        val textMainBefore = text_main.text
        val colorBefore = this.colorWideButton
        val iconVisibilityBefore = icon.visibility
        val priceVisibilityBefore = text_price.visibility

        showAddedItemToCart()

        Handler().postDelayed({
            text_main.text =textMainBefore
            setColor(colorBefore)
            if( priceVisibilityBefore == View.VISIBLE) showPrice()
            if( iconVisibilityBefore == View.VISIBLE) showIcon()
            parent.isClickable = true
            parent.isFocusable = true
        }, 1000)
    }

}