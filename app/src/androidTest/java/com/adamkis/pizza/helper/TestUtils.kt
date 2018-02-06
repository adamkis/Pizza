package com.adamkis.pizza.helper

import android.support.test.InstrumentationRegistry


/**
 * Created by adam on 2017. 12. 31..
 */


object TestUtils{

    fun getString(id: Int): String {
        return InstrumentationRegistry.getTargetContext().resources.getString(id)
    }

}