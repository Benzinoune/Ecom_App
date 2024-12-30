package com.example.ecomapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomapp.activities.MainActivity
import com.example.ecomapp.adapters.ProductAdapter
import com.example.ecomapp.adapters.ImageSliderAdapter
import com.example.ecomapp.models.Product
import com.example.ecomapp.utils.CartManager
import com.example.ecomapp.utils.FavoriteManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.RangeSlider
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.abdo.ecomapp.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private lateinit var productAdapter: ProductAdapter
    private lateinit var products: MutableList<Product>
    private lateinit var sliderProducts: List<Product>
    private lateinit var searchView: SearchView
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        products = createDummyProducts().toMutableList()
        sliderProducts = createSliderProducts()

        setupViewPager(view)
        setupTabLayout(view)
        setupProductList(view)
        setupSearch(view)

        view.findViewById<View>(R.id.filterButton).setOnClickListener {
            showFilterDialog()
        }
    }

    private fun setupViewPager(view: View) {
        viewPager = view.findViewById(R.id.viewPager)
        val sliderAdapter = ImageSliderAdapter(sliderProducts) { product ->
            addToCart(product)
        }
        viewPager.adapter = sliderAdapter
    }

    private fun setupTabLayout(view: View) {
        tabLayout = view.findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // You can set custom tab text or leave it empty for just dots
        }.attach()
    }

    private fun setupProductList(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.productsRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        productAdapter = ProductAdapter(
            products,
            onProductClick = { product ->
                navigateToProductDetails(product)
            },
            onFavoriteClick = { product ->
                toggleFavorite(product)
            },
            onAddToCartClick = { product ->
                addToCart(product)
            }
        )
        recyclerView.adapter = productAdapter
    }

    private fun addToCart(product: Product) {
        CartManager.addToCart(product)
        (activity as? MainActivity)?.updateCartBadge()
        Toast.makeText(context, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
    }

    private fun toggleFavorite(product: Product) {
        if (FavoriteManager.isFavorite(product)) {
            FavoriteManager.removeFromFavorites(product)
        } else {
            FavoriteManager.addToFavorites(product)
        }
        productAdapter.notifyDataSetChanged()
    }

    private fun setupSearch(view: View) {
        searchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterProducts(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterProducts(newText)
                return true
            }
        })
    }

    private fun filterProducts(query: String?) {
        val filteredList = if (query.isNullOrEmpty()) {
            createDummyProducts()
        } else {
            createDummyProducts().filter {
                it.name.lowercase().contains(query.lowercase())
            }
        }
        productAdapter.updateProducts(filteredList)
    }

    private fun showFilterDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.filter_bottom_sheet, null)

        val priceRangeSlider = view.findViewById<RangeSlider>(R.id.priceRangeSlider)
        val applyFilterButton = view.findViewById<Button>(R.id.applyFilterButton)

        applyFilterButton.setOnClickListener {
            val priceRange = priceRangeSlider.values
            val minPrice = priceRange[0]
            val maxPrice = priceRange[1]

            val selectedCategories = mutableListOf<String>()
            if (view.findViewById<CheckBox>(R.id.categorySneakers).isChecked) selectedCategories.add("Sneakers")
            if (view.findViewById<CheckBox>(R.id.categoryRunning).isChecked) selectedCategories.add("Running")
            if (view.findViewById<CheckBox>(R.id.categoryCasual).isChecked) selectedCategories.add("Casual")
            if (view.findViewById<CheckBox>(R.id.categorySports).isChecked) selectedCategories.add("Sports")

            applyFilters(minPrice, maxPrice, selectedCategories)
            dialog.dismiss()
        }

        dialog.setContentView(view)
        dialog.show()
    }

    private fun applyFilters(minPrice: Float, maxPrice: Float, categories: List<String>) {
        val filteredProducts = createDummyProducts().filter { product ->
            product.price in minPrice..maxPrice &&
                    (categories.isEmpty() || categories.any { it in product.name })
        }
        productAdapter.updateProducts(filteredProducts)
    }

    private fun navigateToProductDetails(product: Product) {
        val fragment = ProductDetailsFragment.newInstance(product)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun createDummyProducts(): List<Product> {
        return listOf(
            Product(1, "Sports Shoe", 100.00, R.drawable.shoe1, 4.5f),
            Product(2, "Running Shoe", 120.00, R.drawable.shoe2, 4.0f),
            Product(3, "Training Shoe", 90.00, R.drawable.shoe3, 4.8f),
            Product(4, "Casual Shoe", 80.00, R.drawable.shoe4, 4.2f),
            Product(5, "Sports Shoe", 100.00, R.drawable.shoe5, 4.5f),
            Product(6, "Running Shoe", 120.00, R.drawable.shoe6, 4.0f),
            Product(7, "Training Shoe", 90.00, R.drawable.shoe7, 4.8f),
            Product(8, "Casual Shoe", 80.00, R.drawable.shoe8, 4.2f),
            Product(9, "Sports Shoe", 100.00, R.drawable.shoe9, 4.5f),
            Product(10, "Running Shoe", 120.00, R.drawable.shoe10, 4.0f)
        )
    }

    private fun createSliderProducts(): List<Product> {
        return listOf(
            Product(5, "Featured Sports Shoe", 150.00, R.drawable.shoe5, 4.7f),
            Product(6, "Limited Edition Running Shoe", 180.00, R.drawable.shoe10, 4.9f),
            Product(7, "New Arrival Training Shoe", 130.00, R.drawable.shoe8, 4.6f)
        )
    }
}

