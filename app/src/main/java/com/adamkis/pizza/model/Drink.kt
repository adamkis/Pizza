package com.adamkis.pizza.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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