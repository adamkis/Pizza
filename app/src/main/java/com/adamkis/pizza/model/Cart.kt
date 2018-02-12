package com.adamkis.pizza.model

data class Cart(var orderItems: MutableList<OrderItem> = ArrayList()){

    fun getItemCount() = orderItems.size

    fun addOrderItem(orderItem: OrderItem?){
        orderItem?.let {
            orderItems.add(it)
        }
    }

    fun getTotalPrice(): Double = orderItems.sumByDouble { it.getItemPrice() }

    class Order(val pizzas: ArrayList<Pizza>, val drinks: ArrayList<Int>)

    fun getOrder(): Order{
        var pizzas = ArrayList<Pizza>()
        var drinkIds = ArrayList<Int>()
        for ( orderItem in orderItems ){
            when( orderItem ){
                is Pizza -> pizzas.add(orderItem)
                is Drink -> orderItem.id?.let { drinkIds.add(it) }
            }
        }
        return Order(pizzas, drinkIds)
    }

}