package com.example.ecomapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdo.ecomapp.R
import com.example.ecomapp.models.Product

class ImageSliderAdapter(
    private val products: List<Product>,
    private val onAddToCartClick: (Product) -> Unit
) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.sliderImageView)
        val productName: TextView = view.findViewById(R.id.sliderProductName)
        val productPrice: TextView = view.findViewById(R.id.sliderProductPrice)
        val addToCartButton: Button = view.findViewById(R.id.sliderAddToCartButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_slider, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val product = products[position]
        holder.imageView.setImageResource(product.imageResource)
        holder.productName.text = product.name
        holder.productPrice.text = "$${product.price}"
        holder.addToCartButton.setOnClickListener { onAddToCartClick(product) }
    }

    override fun getItemCount() = products.size
}

