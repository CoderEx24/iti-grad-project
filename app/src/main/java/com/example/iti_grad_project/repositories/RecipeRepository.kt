package com.example.iti_grad_project.repositories

import com.example.iti_grad_project.data.local.FavoriteRecipe
import com.example.iti_grad_project.data.local.RecipeDao
import com.example.iti_grad_project.data.remote.RecipeApi
import com.example.iti_grad_project.data.remote.RecipeApiImp


class RecipeRepository(
    private val api: RecipeApiImp,
    private val dao: RecipeDao
) {

    // remote
    suspend fun searchByName(name: String) = api.searchByName(name)

    suspend fun searchMealByFirstLetter(letter: Char) = api.searchMealByFirstLetter(letter)

    suspend fun searchById(id: String) = api.searchById(id)

    suspend fun getRandomMeal() = api.getRandomMeal()

    suspend fun getAllCategories() = api.getAllCategories()

    suspend fun getAllAreas() = api.getAllAreas()

    suspend fun getAllIngredients() = api.getAllIngredients()

    suspend fun getCategoryMeals(category: String) = api.getCategoryMeals(category)

    suspend fun getAreaMeals(area: String) = api.getAreaMeals(area)

    suspend fun getIngredientMeals(ingredient: String) = api.getIngredientMeals(ingredient)


    // local
    suspend fun addFavourite(recipe: FavoriteRecipe) = dao.insertFavorite(recipe)

    suspend fun deleteFavourite(recipe: FavoriteRecipe) = dao.deleteFavorite(recipe)

    suspend fun getAllFavourites(username: String) = dao.getAllFavorites(username)

}
