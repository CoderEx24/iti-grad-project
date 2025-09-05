package com.example.iti_grad_project.data.remote

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable
import java.io.Serial

data class RecipeResponse(
    val meals: List<Meal>
)
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String? = null,
    val strArea: String? = null,
    val strInstructions: String? = null,
    val strMealThumb: String? = null,
    val strYoutube: String? = null,
    val strSource: String? = null
)

data class CategoryResponse(
    val categories: List<String>
)

data class AreaResponse(
    val areas: List<String>
)

data class IngredientResponse(
    val ingredients: List<Ingredient>
)

data class Ingredient(
    val idIngredient: String,
    val strIngredient: String,
    val strDescription: String? = null,
    val strType: String? = null
)