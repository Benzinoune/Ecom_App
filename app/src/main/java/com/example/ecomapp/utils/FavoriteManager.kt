package com.example.ecomapp.utils

import com.example.ecomapp.models.Product

object FavoriteManager {
    private val favoriteProducts = mutableSetOf<Product>()

    fun addToFavorites(product: Product) {
        favoriteProducts.add(product)
    }

    fun removeFromFavorites(product: Product) {
        favoriteProducts.remove(product)
    }

    fun isFavorite(product: Product): Boolean {
        return favoriteProducts.contains(product)
    }

    fun getFavoriteProducts(): List<Product> {
        return favoriteProducts.toList()
    }
}

