package com.adamkis.pizza.model

/**
 * Created by adam on 2018. 02. 09..
 */

interface OrderItem{
    fun getItemName(): String
    fun getItemPrice(): Double
}