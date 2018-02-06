package com.adamkis.pizza.network


/**
 * Created by adam on 2018. 01. 02..
 */

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.PrintWriter
import java.io.StringWriter

val FLICKR_URL_BASE = "https://api.myjson.com/bins/"

fun <T> callback(success: (Response<T>) -> Unit, failure: (t: Throwable) -> Unit): Callback<T>? {
    return object : Callback<T> {
        override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) = success(response)
        override fun onFailure(call: Call<T>, t: Throwable) = failure(t)
    }
}

fun getStackTrace(throwable: Throwable): String {
    val sw = StringWriter()
    val pw = PrintWriter(sw, true)
    throwable.printStackTrace(pw)
    return sw.buffer.toString()
}