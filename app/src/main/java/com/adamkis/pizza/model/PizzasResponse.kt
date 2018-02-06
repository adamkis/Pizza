package com.adamkis.pizza.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by adam on 2018. 02. 6.
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class PizzasResponse(var basePrice: Int?, var pizzas: Array<Pizza>?) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PizzasResponse

        if (basePrice != other.basePrice) return false
        if (!Arrays.equals(pizzas, other.pizzas)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = basePrice ?: 0
        result = 31 * result + (pizzas?.let { Arrays.hashCode(it) } ?: 0)
        return result
    }
}