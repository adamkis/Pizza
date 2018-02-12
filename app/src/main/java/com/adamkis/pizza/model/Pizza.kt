package com.adamkis.pizza.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*


/**
 * Created by adam on 2017. 01. 14..
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class Pizza(@SerializedName("name") var name: String?,
                 @SerializedName("imageUrl") var imageUrl: String?,
                 @SerializedName("ingredients") private var ingredientIds: ArrayList<Int>?,
                 private var ingredientObjects: ArrayList<Ingredient>?,
                 var basePrice: Double? = 0.0
        ) : OrderItem, Parcelable{

    override fun getItemName(): String {
        return name ?: ""
    }

    override fun getItemPrice(): Double {
        var price: Double = 0.0
        ingredientObjects?.let {
            for (ingredient in it) {
                price += ingredient.price ?: 0.0
            }
        }
        price += basePrice ?: 0.0
        return price
    }

    fun addIngredient(ingredient: Ingredient?){
        if (ingredientObjects == null) ingredientObjects = ArrayList()
        ingredient?.let {
            ingredient ->
                ingredientObjects?.add(ingredient)
                ingredient.id?.let { id -> ingredientIds?.add(id) }
        }
    }

    fun removeIngredient(ingredient: Ingredient?){
        ingredient?.let {
            ingredient ->
                ingredientObjects?.remove(ingredient)
                ingredient.id?.let { id -> ingredientIds?.removeAll(Arrays.asList(id)) }
        }
    }

    fun getIngredientObjects(): ArrayList<Ingredient>?{
        return ingredientObjects
    }

    fun getIngredientIds(): ArrayList<Int>?{
        return ingredientIds
    }

    fun initIngredientObjects(ingredientsHM: HashMap<Int?, Ingredient>?){
        if (ingredientObjects == null) ingredientObjects = ArrayList()
        ingredientIds?.forEach {
            ingredientId ->
            ingredientsHM?.get(ingredientId)?.let {
                ingredient ->ingredientObjects?.add(ingredient)
            }
        }
    }

}