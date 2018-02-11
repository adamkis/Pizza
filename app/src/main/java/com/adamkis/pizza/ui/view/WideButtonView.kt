package com.adamkis.pizza.ui.view

import android.content.Context
import android.os.Handler
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v7.widget.AppCompatCheckBox
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.adamkis.pizza.R

/**
 * Created by adam on 2018. 02. 07..
 */
class WideButtonView : FrameLayout {

    lateinit private var icon: ImageView
    lateinit private var text_main: TextView
    lateinit private var text_price: TextView
    lateinit private var container: View
    lateinit private var color: Color

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

    private fun initView() {
        val view = View.inflate(context, R.layout.view_wide_button, null)
        icon = view.findViewById(R.id.icon)
        text_main = view.findViewById(R.id.text_main)
        text_price = view.findViewById(R.id.text_price)
        container = view.findViewById(R.id.container)
        addView(view)
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

    fun setTextMain(@StringRes stringId: Int){
        text_main.text = context.getString(stringId)
    }

    fun setTextPrice(price: Double?){
        text_price.text = context.getString(R.string.item_price, price)
    }

    fun setColor(color: Color){
        when( color ){
            Color.ORANGE -> {
                container.setBackgroundResource(R.color.bg_orange)
                this.color = Color.ORANGE
            }
            Color.RED -> {
                container.setBackgroundResource(R.color.colorAccent)
                this.color = Color.RED
            }
        }
    }

    fun showAddedToCartFlash(){
        val textMainBefore = text_main.text
        val colorBefore = this.color

        text_main.text = context.getString(R.string.added_to_cart)
        hidePrice()
        hideIcon()
        setColor(Color.RED)

        Handler().postDelayed({
            text_main.text =textMainBefore
            showPrice()
            showIcon()
            setColor(colorBefore)
        }, 1000)
    }

}