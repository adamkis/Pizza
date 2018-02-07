package com.adamkis.pizza.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by adam on 2017. 01. 14..
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class Pizza(var name: String?, var imageUrl: String?, var ingredients: IntArray?) : Parcelable{

    companion object {
        const val TAG = "PIZZA"
    }

}