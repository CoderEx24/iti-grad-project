package com.example.iti_grad_project.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipes")
data class FavoriteRecipe(
    @PrimaryKey val idMeal: String,
    val username: String,
    val strMeal: String,
    val strMealThumb: String?
)
