package com.example.ecomapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.ecomapp.models.User
import com.google.gson.Gson

object PreferenceHelper {
    private const val PREF_NAME = "EcomAppPrefs"
    private const val KEY_USER = "user"
    private const val KEY_LOGGED_IN = "logged_in"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun isLoggedIn(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_LOGGED_IN, false)
    }

    fun login(context: Context, email: String, password: String): Boolean {
        val user = getUser(context)
        return if (user != null && user.email == email && user.password == password) {
            getSharedPreferences(context).edit().putBoolean(KEY_LOGGED_IN, true).apply()
            true
        } else {
            false
        }
    }

    fun signup(context: Context, name: String, email: String, password: String): Boolean {
        if (getUser(context) != null) {
            return false
        }
        val user = User(name, email, password)
        val gson = Gson()
        val json = gson.toJson(user)
        getSharedPreferences(context).edit().putString(KEY_USER, json).apply()
        return true
    }

    fun logout(context: Context) {
        getSharedPreferences(context).edit().putBoolean(KEY_LOGGED_IN, false).apply()
    }

    fun getUser(context: Context): User? {
        val json = getSharedPreferences(context).getString(KEY_USER, null)
        return if (json != null) {
            Gson().fromJson(json, User::class.java)
        } else {
            null
        }
    }
}

