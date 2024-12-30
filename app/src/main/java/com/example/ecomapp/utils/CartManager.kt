package com.example.ecomapp.utils


import com.example.ecomapp.models.Product
import com.example.ecomapp.models.CartItem

object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    fun addToCart(product: Product) {
        val existingItem = cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(CartItem(product, 1))
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        cartItems.remove(cartItem)
    }

    fun updateQuantity(cartItem: CartItem, quantity: Int) {
        val existingItem = cartItems.find { it.product.id == cartItem.product.id }
        existingItem?.quantity = quantity
        if (existingItem?.quantity == 0) {
            removeFromCart(existingItem)
        }
    }

    fun getCartItems(): List<CartItem> = cartItems

    fun clearCart() {
        cartItems.clear()
    }

    fun getTotalQuantity(): Int {
        return cartItems.sumOf { it.quantity }
    }
}

