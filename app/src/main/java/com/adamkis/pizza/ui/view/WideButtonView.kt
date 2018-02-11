package com.adamkis.pizza.ui.view

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
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
        addView(view)
    }

    fun setIcon(@DrawableRes drawableId: Int){
        icon.setImageResource(drawableId)
    }

    fun hideIcon(){
        icon.visibility = GONE
    }

    fun hidePrice(){
        text_price.visibility = GONE
    }

    fun setTextMain(textMain: String?){
        text_main.text = textMain
    }

    fun setTextPrice(price: Double?){
        text_price.text = context.getString(R.string.item_price, price)
    }

}