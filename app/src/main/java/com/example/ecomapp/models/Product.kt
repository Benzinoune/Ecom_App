package com.example.ecomapp.models


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageResource: Int,
    val rating: Float,
    var isFavorite: Boolean = false,
    var quantity: Int = 0
) : Parcelable

