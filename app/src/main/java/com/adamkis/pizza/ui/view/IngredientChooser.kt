package com.adamkis.pizza.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.adamkis.pizza.R


/**
 * Created by adam on 2018. 02. 07..
 */
class IngredientChooser : FrameLayout {

    lateinit private var ingredientName: TextView
    lateinit private var ingredientPrice: TextView

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
        val view = View.inflate(context, R.layout.ingredient_chooser, null)
        ingredientName = view.findViewById(R.id.ingredient_name)
        ingredientPrice = view.findViewById(R.id.ingredient_price)
        addView(view)
    }

    public fun setName(name: String?){
        ingredientName.text = name
    }

    public fun setPrice(price: Double?){
        ingredientPrice.text = context.getString(R.string.price_with_currency, price)
    }
}
