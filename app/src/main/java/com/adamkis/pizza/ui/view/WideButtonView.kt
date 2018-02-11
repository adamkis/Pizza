package com.adamkis.pizza.ui.view

import android.content.Context
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
open class WideButtonView : FrameLayout {

    lateinit private var icon: ImageView
    lateinit private var text_main: TextView
    lateinit private var text_price: TextView
    lateinit private var container: View
    lateinit private var parent: View
    lateinit private var colorWB: Color

    enum class Color { ORANGE, RED }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context) : super(context) {
        initView()
    }

    protected open fun initView() {
        val view = View.inflate(context, R.layout.view_wide_button, null)
        icon = view.findViewById(R.id.icon)
        text_main = view.findViewById(R.id.text_main)
        text_price = view.findViewById(R.id.text_price)
        container = view.findViewById(R.id.container)
        addView(view)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        parent = container.parent as View
    }

    fun setIcon(@DrawableRes drawableId: Int){
        icon.setImageResource(drawableId)
    }

    fun hideIcon(){
        icon.visibility = GONE
    }

    fun showIcon(){
        icon.visibility = VISIBLE
    }

    fun hidePrice(){
        text_price.visibility = GONE
    }

    fun showPrice(){
        text_price.visibility = VISIBLE
    }

    fun setMainText(@StringRes stringId: Int){
        text_main.text = context.getString(stringId)
    }

    fun setPriceText(price: Double?){
        text_price.text = context.getString(R.string.item_price, price)
    }

    fun setClickableWB(clickable: Boolean){
        if( clickable ){
            parent.isClickable = true
            parent.isFocusable = true
        }
        else{
            parent.isClickable = false
            parent.isFocusable = false
        }
    }

    fun setColor(color: Color){
        when( color ){
            Color.ORANGE -> {
                container.setBackgroundResource(R.color.bg_orange)
                this.colorWB = Color.ORANGE
            }
            Color.RED -> {
                container.setBackgroundResource(R.color.colorAccent)
                this.colorWB = Color.RED
            }
        }
    }

    fun showAddedItemToCart(){
        text_main.text = context.getString(R.string.added_to_cart)
        setColor(Color.RED)
        hidePrice()
        hideIcon()
        setClickableWB(false)
    }

}