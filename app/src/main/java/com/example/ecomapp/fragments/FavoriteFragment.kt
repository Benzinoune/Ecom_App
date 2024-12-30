package com.example.ecomapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdo.ecomapp.R
import com.example.ecomapp.activities.MainActivity
import com.example.ecomapp.adapters.ProductAdapter
import com.example.ecomapp.models.Product
import com.example.ecomapp.utils.CartManager
import com.example.ecomapp.utils.FavoriteManager

class FavoriteFragment : Fragment() {
    private lateinit var productAdapter: ProductAdapter
    private var favoriteProducts = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.favoriteRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        productAdapter = ProductAdapter(
            favoriteProducts,
            onProductClick = { product ->
                navigateToProductDetails(product)
            },
            onFavoriteClick = { product ->
                removeFromFavorites(product)
            },
            onAddToCartClick = { product ->
                CartManager.addToCart(product)
                (activity as? MainActivity)?.updateCartBadge()
            }
        )
        recyclerView.adapter = productAdapter

        updateFavorites()
    }

    private fun updateFavorites() {
        favoriteProducts.clear()
        favoriteProducts.addAll(FavoriteManager.getFavoriteProducts())
        productAdapter.notifyDataSetChanged()
    }

    private fun removeFromFavorites(product: Product) {
        FavoriteManager.removeFromFavorites(product)
        updateFavorites()
    }

    private fun navigateToProductDetails(product: Product) {
        val fragment = ProductDetailsFragment.newInstance(product)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        updateFavorites()
    }
}

