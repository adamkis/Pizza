package com.adamkis.pizza.model

interface OrderItem{
    fun getItemName(): String
    fun getItemPrice(): Double
}