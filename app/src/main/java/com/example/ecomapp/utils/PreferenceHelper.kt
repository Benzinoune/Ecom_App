package com.example.ecomapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.ecomapp.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PreferenceHelper {
    private const val PREF_NAME = "EcomAppPrefs"
    private const val KEY_USERS = "users"
    private const val KEY_LOGGED_IN_USER = "logged_in_user"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun isLoggedIn(context: Context): Boolean {
        return getLoggedInUser(context) != null
    }

    fun login(context: Context, email: String, password: String): Boolean {
        val users = getUsers(context)
        val user = users.find { it.email == email && it.password == password }
        if (user != null) {
            setLoggedInUser(context, user)
            return true
        }
        return false
    }

    fun signup(context: Context, name: String, email: String, password: String): Boolean {
        val users = getUsers(context).toMutableList()
        if (users.any { it.email == email }) {
            return false
        }
        val newUser = User(name, email, password)
        users.add(newUser)
        saveUsers(context, users)
        return true
    }

    fun logout(context: Context) {
        getSharedPreferences(context).edit().remove(KEY_LOGGED_IN_USER).apply()
    }

    fun getLoggedInUser(context: Context): User? {
        val json = getSharedPreferences(context).getString(KEY_LOGGED_IN_USER, null)
        return if (json != null) {
            Gson().fromJson(json, User::class.java)
        } else {
            null
        }
    }

    private fun setLoggedInUser(context: Context, user: User) {
        val json = Gson().toJson(user)
        getSharedPreferences(context).edit().putString(KEY_LOGGED_IN_USER, json).apply()
    }

    private fun getUsers(context: Context): List<User> {
        val json = getSharedPreferences(context).getString(KEY_USERS, null)
        return if (json != null) {
            Gson().fromJson(json, object : TypeToken<List<User>>() {}.type)
        } else {
            emptyList()
        }
    }

    private fun saveUsers(context: Context, users: List<User>) {
        val json = Gson().toJson(users)
        getSharedPreferences(context).edit().putString(KEY_USERS, json).apply()
    }
}

