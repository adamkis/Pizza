package com.adamkis.pizza.helper

import timber.log.Timber

fun logDebug(message: String?){
    // TODO: send to crashlytics
    Timber.d(message)
}

fun logThrowable(t: Throwable?){
    // TODO: send to crashlytics
    Timber.e(t)
}