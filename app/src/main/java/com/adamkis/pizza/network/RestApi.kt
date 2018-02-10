package com.adamkis.pizza.network

import com.adamkis.pizza.model.Cart
import com.adamkis.pizza.model.Drink
import com.adamkis.pizza.model.Ingredient
import com.adamkis.pizza.model.PizzasResponse
import io.reactivex.Observable
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.http.*


/**
 * Created by adam on 2018. 01. 05..
 */
interface RestApi {

    companion object {
        val PIZZA_URL_BASE = "https://api.myjson.com/bins/"
        val PIZZA_ORDER_POST_URL = "http://httpbin.org/post"
    }

    @GET("dokm7")
    fun getPizzas(): Observable<PizzasResponse>

    @GET("ozt3z")
    fun getIngredients(): Observable<Array<Ingredient>>

    @GET("150da7")
    fun getDrinks(): Observable<Array<Drink>>

    @POST
    fun order(@Body order: Cart.Order, @Url url: String = PIZZA_ORDER_POST_URL): Observable<ResponseBody>

}