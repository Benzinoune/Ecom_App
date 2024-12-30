package com.example.ecomapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdo.ecomapp.R
import com.example.ecomapp.models.Product
import com.example.ecomapp.utils.FavoriteManager

class ProductAdapter(
    private var products: List<Product>,
    private val onProductClick: (Product) -> Unit,
    private val onFavoriteClick: (Product) -> Unit,
    private val onAddToCartClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.productImage)
        val productName: TextView = view.findViewById(R.id.productName)
        val productPrice: TextView = view.findViewById(R.id.productPrice)
        val favoriteButton: ImageButton = view.findViewById(R.id.favoriteButton)
        val addToCartButton: ImageButton = view.findViewById(R.id.addToCartButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        holder.productImage.setImageResource(product.imageResource)
        holder.productName.text = product.name
        holder.productPrice.text = "$${product.price}"

        updateFavoriteButton(holder.favoriteButton, product)

        holder.favoriteButton.setOnClickListener {
            onFavoriteClick(product)
            updateFavoriteButton(holder.favoriteButton, product)
        }

        holder.addToCartButton.setOnClickListener {
            onAddToCartClick(product)
        }

        holder.itemView.setOnClickListener { onProductClick(product) }
    }

    private fun updateFavoriteButton(button: ImageButton, product: Product) {
        button.setImageResource(
            if (FavoriteManager.isFavorite(product)) R.drawable.ic_favorite_filled
            else R.drawable.ic_favorite
        )
    }

    override fun getItemCount() = products.size

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}

