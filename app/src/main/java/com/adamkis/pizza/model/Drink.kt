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
class Drink(var name: String?, var price: Double?, var id: Int?): OrderItem, Parcelable {
    override fun getItemName(): String {
        return name ?: ""
    }

    override fun getItemPrice(): Double {
        return price ?: 0.0
    }
}