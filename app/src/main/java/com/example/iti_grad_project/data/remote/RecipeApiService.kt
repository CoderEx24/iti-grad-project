package com.example.iti_grad_project.data.remote

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object RecipeApiService: RecipeApiImp {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val services = retrofit.create(RecipeApi::class.java)


    override suspend fun searchByName(name: String): RecipeResponse {
        services.searchByName(name)
        TODO("Not yet implemented")
    }

    override suspend fun searchMealByFirstLetter(letter: Char): RecipeResponse {
        services.searchMealByFirstLetter(letter)
        TODO("Not yet implemented")
    }

    override suspend fun searchById(id: String): RecipeResponse {
        services.searchById(id)
        TODO("Not yet implemented")
    }

    override suspend fun getRandomMeal(): RecipeResponse {
        services.getRandomMeal()
        TODO("Not yet implemented")
    }

    override suspend fun getAllCategories(): CategoryResponse {
        services.getAllCategories()
        TODO("Not yet implemented")
    }

    override suspend fun getAllAreas(): AreaResponse {
        services.getAllAreas()
        TODO("Not yet implemented")
    }

    override suspend fun getAllIngredients(): IngredientResponse {
        services.getAllIngredients()
        TODO("Not yet implemented")
    }

    override suspend fun getCategoryMeals(category: String): RecipeResponse {
        services.getCategoryMeals(category)
        TODO("Not yet implemented")
    }

    override suspend fun getIngredientMeals(ingredient: String): RecipeResponse {
        services.getIngredientMeals(ingredient)
        TODO("Not yet implemented")
    }

    override suspend fun getAreaMeals(area: String): RecipeResponse {
        services.getAreaMeals(area)
        TODO("Not yet implemented")
    }
}

interface RecipeApiImp{

    // search
    suspend fun searchByName(name: String): RecipeResponse

    suspend fun searchMealByFirstLetter(letter: Char): RecipeResponse



    // get single meal details
    suspend fun searchById(id: String): RecipeResponse

    suspend fun getRandomMeal(): RecipeResponse



    suspend fun getAllCategories(): CategoryResponse


    suspend fun getAllAreas(): AreaResponse

    suspend fun getAllIngredients(): IngredientResponse



    // filter
    suspend fun getCategoryMeals( category: String): RecipeResponse

    suspend fun getIngredientMeals(ingredient: String): RecipeResponse

    suspend fun getAreaMeals(area: String): RecipeResponse

}