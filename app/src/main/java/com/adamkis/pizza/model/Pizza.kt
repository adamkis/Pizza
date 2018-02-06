package com.adamkis.pizza.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by adam on 2017. 01. 14..
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class Pizza(var name: String?, var imageUrl: String?, var ingredients: Array<Integer>?) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pizza

        if (name != other.name) return false
        if (imageUrl != other.imageUrl) return false
        if (!Arrays.equals(ingredients, other.ingredients)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (imageUrl?.hashCode() ?: 0)
        result = 31 * result + (ingredients?.let { Arrays.hashCode(it) } ?: 0)
        return result
    }
}