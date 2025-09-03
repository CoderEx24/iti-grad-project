package com.example.iti_grad_project.data.prefs

import android.content.Context
import androidx.core.content.edit

class PreferenceManager(context: Context) {
    private val prefs = context.getSharedPreferences("recipe_pref", Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit { putBoolean("isLoggedIn", isLoggedIn) }
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("isLoggedIn", false)

    fun setUsername(username: String) {
        prefs.edit { putString("username", username) }
    }

    fun getUsername(): String? = prefs.getString("username", null)

    fun clearUsername() {
        prefs.edit { remove("username").apply() }
    }

}