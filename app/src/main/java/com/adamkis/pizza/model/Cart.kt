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

    fun getOrder(): Order{
        var pizzas = ArrayList<Pizza>()
        var drinkIds = ArrayList<Int>()
        for ( orderItem in orderItems ){
            when( orderItem ){
                is Pizza -> pizzas.add(orderItem)
                // TODO null check
                is Drink -> drinkIds.add(orderItem.id!!)
            }
        }
        return Order(pizzas, drinkIds)
    }

    class Order(val pizzas: ArrayList<Pizza>, val drinks: ArrayList<Int>)

}