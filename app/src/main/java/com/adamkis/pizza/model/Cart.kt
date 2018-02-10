package com.adamkis.pizza.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by adam on 2017. 01. 14..
 */

data class Cart(var totalPrice: Double? = 0.0,
                var orderItems: MutableList<OrderItem> = ArrayList()){

    fun getItemCount() = orderItems.size

    fun addOrderItem(orderItem: OrderItem?){
        orderItem?.let {
            orderItems.add(it)
        }
    }

}