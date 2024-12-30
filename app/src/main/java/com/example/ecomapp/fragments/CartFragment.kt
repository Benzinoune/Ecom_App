package com.example.ecomapp.fragments

import com.abdo.ecomapp.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomapp.activities.MainActivity
import com.example.ecomapp.adapters.CartAdapter
import com.example.ecomapp.models.CartItem
import com.example.ecomapp.utils.CartManager

class CartFragment : Fragment() {
    private lateinit var cartAdapter: CartAdapter
    private var cartItems = mutableListOf<CartItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.cartRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        cartAdapter = CartAdapter(
            cartItems,
            onQuantityChanged = { cartItem, quantity ->
                CartManager.updateQuantity(cartItem, quantity)
                updateCart()
                (activity as? MainActivity)?.updateCartBadge()
            },
            onRemoveClick = { cartItem ->
                CartManager.removeFromCart(cartItem)
                updateCart()
                (activity as? MainActivity)?.updateCartBadge()
            }
        )
        recyclerView.adapter = cartAdapter

        updateCart()
    }

    private fun updateCart() {
        cartItems.clear()
        cartItems.addAll(CartManager.getCartItems())
        cartAdapter.notifyDataSetChanged()
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val totalPrice = cartItems.sumOf { it.product.price * it.quantity }
        view?.findViewById<TextView>(R.id.totalPriceTextView)?.text = "Total: $${String.format("%.2f", totalPrice)}"
    }
}

