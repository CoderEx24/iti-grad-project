package com.example.iti_grad_project.utils

const val baseUrl = "https://www.themealdb.com/api/json/v1/1/"
const val imageUrl = "https://www.themealdb.com/images/ingredients/"

const val ERROR_TAG = "ERROR"

enum class SearchFilter(val isCategory: Boolean, val isArea: Boolean, val isIngredient: Boolean) {
    CATEGORY(true, false, false),
    AREA(false, true, false),
    INGREDIENT(false, false, true);
}