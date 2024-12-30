package com.example.ecomapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.abdo.ecomapp.R
import com.example.ecomapp.activities.MainActivity
import com.example.ecomapp.models.Product
import com.example.ecomapp.utils.CartManager

class ProductDetailsFragment : Fragment() {
    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getParcelable(ARG_PRODUCT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        product?.let { product ->
            view.findViewById<ImageView>(R.id.productImage).setImageResource(product.imageResource)
            view.findViewById<TextView>(R.id.productName).text = product.name
            view.findViewById<TextView>(R.id.productPrice).text = "$${product.price}"

            view.findViewById<Button>(R.id.addToCartButton).setOnClickListener {
                addToCart(product)
            }
        }

        view.findViewById<View>(R.id.backButton).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun addToCart(product: Product) {
        CartManager.addToCart(product)
        (activity as? MainActivity)?.updateCartBadge()
    }

    companion object {
        private const val ARG_PRODUCT = "product"

        fun newInstance(product: Product) = ProductDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_PRODUCT, product)
            }
        }
    }
}

