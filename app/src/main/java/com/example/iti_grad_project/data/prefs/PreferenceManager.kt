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

    // 🔹 Recipe of the day storage
    fun setRecipeOfTheDay(recipeJson: String) {
        clearRecipeOfTheDay()
        prefs.edit {
            putString("recipe_of_day", recipeJson)
            putLong("recipe_of_day_timestamp", System.currentTimeMillis())
        }
    }

    fun getRecipeOfTheDay(): String? = prefs.getString("recipe_of_day", null)

    fun getRecipeOfTheDayTimestamp(): Long =
        prefs.getLong("recipe_of_day_timestamp", 0L)

    fun clearRecipeOfTheDay() {
        prefs.edit {
            remove("recipe_of_day")
            remove("recipe_of_day_timestamp")
        }
    }
}