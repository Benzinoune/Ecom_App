package com.example.ecomapp.adapters

import com.abdo.ecomapp.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomapp.models.CartItem

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val onQuantityChanged: (CartItem, Int) -> Unit,
    private val onRemoveClick: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.productImage)
        val productName: TextView = view.findViewById(R.id.productName)
        val productPrice: TextView = view.findViewById(R.id.productPrice)
        val productQuantity: TextView = view.findViewById(R.id.productQuantity)
        val removeButton: ImageButton = view.findViewById(R.id.removeButton)
        val increaseQuantityButton: ImageButton = view.findViewById(R.id.increaseQuantityButton)
        val decreaseQuantityButton: ImageButton = view.findViewById(R.id.decreaseQuantityButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]

        holder.productImage.setImageResource(cartItem.product.imageResource)
        holder.productName.text = cartItem.product.name
        holder.productPrice.text = "$${cartItem.product.price}"
        holder.productQuantity.text = cartItem.quantity.toString()

        holder.removeButton.setOnClickListener {
            onRemoveClick(cartItem)
        }

        holder.increaseQuantityButton.setOnClickListener {
            onQuantityChanged(cartItem, cartItem.quantity + 1)
        }

        holder.decreaseQuantityButton.setOnClickListener {
            if (cartItem.quantity > 1) {
                onQuantityChanged(cartItem, cartItem.quantity - 1)
            } else {
                onRemoveClick(cartItem)
            }
        }
    }

    override fun getItemCount() = cartItems.size
}


