package com.adamkis.pizza.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by adam on 2017. 01. 14..
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class Basket(var totalPrice: Double? = 0.0,
                  var pizzas: Array<Pizza>? = null
        ) : Parcelable{

        var itemCount: Int = 0
            get() = pizzas?.size ?: 0

}