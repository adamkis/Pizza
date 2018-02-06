package com.adamkis.pizza.network

import com.adamkis.pizza.model.Ingredient
import com.adamkis.pizza.model.PizzasResponse
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by adam on 2018. 01. 05..
 */
interface RestApi {

    @GET("dokm7")
    fun getPizzas(): Observable<PizzasResponse>

    @GET("ozt3z")
    fun getIngredients(): Observable<Array<Ingredient>>

}