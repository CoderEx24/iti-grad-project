package com.example.iti_grad_project.data.remote

import retrofit2.http.Query
import retrofit2.http.GET

interface RecipeApi {

    // search
    @GET("search.php")
    suspend fun searchByName(@Query("s") name: String): RecipeResponse

    @GET("search.php")
    suspend fun searchMealByFirstLetter(@Query("f") letter: Char): RecipeResponse



    // get single meal details
    @GET("lookup.php")
    suspend fun searchById(@Query("i") id: String): RecipeResponse

    @GET("random.php")
    suspend fun getRandomMeal(): RecipeResponse



    // list all
    @GET("list.php?c=list")
    suspend fun getAllCategories(): CategoryResponse

    @GET("list.php?a=list")
    suspend fun getAllAreas(): AreaResponse

    @GET("list.php?i=list")
    suspend fun getAllIngredients(): IngredientResponse



    // filter
    @GET("filter.php")
    suspend fun getCategoryMeals(@Query("c") category: String): RecipeResponse

    @GET("filter.php")
    suspend fun getIngredientMeals(@Query("i") ingredient: String): RecipeResponse

    @GET("filter.php")
    suspend fun getAreaMeals(@Query("a") area: String): RecipeResponse


}