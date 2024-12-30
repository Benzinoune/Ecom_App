package com.example.ecomapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.abdo.ecomapp.R
import com.example.ecomapp.fragments.*
import com.example.ecomapp.utils.PreferenceHelper
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.ecomapp.utils.CartManager

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    private var cartBadge: BadgeDrawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!PreferenceHelper.isLoggedIn(this)) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        bottomNav = findViewById(R.id.bottomNavigation)
        setupBottomNavigation()

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        cartBadge = bottomNav.getOrCreateBadge(R.id.nav_cart)
        cartBadge?.isVisible = false
        updateCartBadge()
    }

    private fun setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_favorite -> loadFragment(FavoriteFragment())
                R.id.nav_cart -> loadFragment(CartFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        return true
    }

    fun updateCartBadge() {
        val totalQuantity = CartManager.getTotalQuantity()
        cartBadge?.apply {
            isVisible = totalQuantity > 0
            number = totalQuantity
        }
    }
}

