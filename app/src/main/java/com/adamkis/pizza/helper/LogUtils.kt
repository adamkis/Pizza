package com.adamkis.pizza.helper

import timber.log.Timber

/**
 * Created by adam on 2018. 02. 06..
 */


fun logDebug(message: String?){
    // TODO: send to crashlytics
    Timber.d(message)
}

fun logThrowable(t: Throwable?){
    // TODO: send to crashlytics
    Timber.e(t)
}