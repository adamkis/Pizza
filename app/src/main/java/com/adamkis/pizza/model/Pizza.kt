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
                 @SerializedName("ingredients") var ingredients: IntArray?
        ) : OrderItem, Parcelable{

    override fun getItemName(): String {
        return name ?: ""
    }

    override fun getItemPrice(): Double {
        // TODO Implement it
        return 3.14
    }

    companion object {
        const val TAG = "PIZZA"
    }

}