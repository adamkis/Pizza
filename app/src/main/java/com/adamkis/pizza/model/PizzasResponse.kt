package com.adamkis.pizza.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlin.collections.HashMap

@SuppressLint("ParcelCreator")
@Parcelize
data class PizzasResponse(var basePrice: Double?, var pizzas: Array<Pizza>?) : Parcelable{

    fun updatePizzaPrices(basePrice: Double?){
        pizzas?.forEach {
            it.basePrice = basePrice
        }
    }


    fun fillPizzaIngredients(ingredientsHM: HashMap<Int?, Ingredient>?){
        pizzas?.forEach { it.initIngredientObjects(ingredientsHM) }
    }

}