package com.adamkis.pizza.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by adam on 2018. 02. 09..
 */

interface OrderItem{
    fun getItemName(): String
    fun getItemPrice(): Double
}