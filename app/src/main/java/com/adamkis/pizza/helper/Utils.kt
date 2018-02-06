package com.adamkis.pizza.helper

/**
 * Created by adam on 2018. 02. 06..
 */


fun <T: Any, R: Any> whenAllNotNull(vararg options: T?, block: (List<T>)->R) {
    if (options.all { it != null }) {
        block(options.filterNotNull())
    }
}

fun <T: Any, R: Any> whenAnyNotNull(vararg options: T?, block: (List<T>)->R) {
    if (options.any { it != null }) {
        block(options.filterNotNull())
    }
}