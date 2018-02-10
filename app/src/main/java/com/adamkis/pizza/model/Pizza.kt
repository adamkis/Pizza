package com.adamkis.pizza.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray
import org.json.JSONObject



/**
 * Created by adam on 2017. 01. 14..
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class Pizza(@SerializedName("name") var name: String?,
                 @SerializedName("imageUrl") var imageUrl: String?,
                 @SerializedName("ingredients") var ingredientIds: IntArray?,
                 var ingredientObjs: ArrayList<Ingredient>,
                 var basePrice: Double? = 0.0
        ) : OrderItem, Parcelable{

    override fun getItemName(): String {
        return name ?: ""
    }

    override fun getItemPrice(): Double {
        var price: Double = 0.0
        for (ingredient in ingredientObjs){
            price += ingredient.price ?: 0.0
        }
        price += basePrice ?: 0.0
        return price
    }

    fun addIngredient(ingredient: Ingredient?){
        // TODO change instantiation
        if (ingredientObjs == null) ingredientObjs = ArrayList()
        ingredient?.let { ingredientObjs.add(it) }
    }


    companion object {
        const val TAG = "PIZZA"
    }

}