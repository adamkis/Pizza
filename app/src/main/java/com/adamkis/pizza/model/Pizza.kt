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

    companion object {
        const val TAG = "PIZZA"
    }

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

    fun addIngredientObject(ingredient: Ingredient?){
        // TODO change instantiation
        if (ingredientObjects == null) ingredientObjects = ArrayList()
        ingredient?.let { ingredientObjects?.add(it) }
    }

    fun removeIngredientObject(ingredient: Ingredient?){
        ingredient?.let { ingredientObjects?.remove(ingredient) }
    }

    // TODO two methods are not needed!
    fun addIngredientId(ingredientId: Int?){
        ingredientId?.let { ingredientIds?.add(it) }
    }

    // TODO two methods are not needed!
    fun removeIngredientId(ingredientId: Int?){
        ingredientId?.let { ingredientIds?.removeAll(Arrays.asList(ingredientId)) }
    }

    fun getIngredients(): ArrayList<Ingredient>?{
        return ingredientObjects
    }

    fun getIngredientIds(): ArrayList<Int>?{
        return ingredientIds
    }

    fun initIngredientObjects(ingredientsHM: HashMap<Int?, Ingredient>?){
        ingredientIds?.forEach {
            ingredientId -> addIngredientObject(ingredientsHM?.get(ingredientId))
        }
    }


}