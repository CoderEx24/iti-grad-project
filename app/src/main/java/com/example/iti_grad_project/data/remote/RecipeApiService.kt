package com.example.iti_grad_project.data.remote

import android.util.Log
import com.example.iti_grad_project.utils.baseUrl
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object RecipeApiService: RecipeApiImp {

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    private val services = retrofit.create(RecipeApi::class.java)


    override suspend fun searchByName(name: String): RecipeResponse {
        return services.searchByName(name)
    }

    override suspend fun searchMealByFirstLetter(letter: Char): RecipeResponse {
        return services.searchMealByFirstLetter(letter)
    }

    override suspend fun searchById(id: String): RecipeResponse {
        return services.searchById(id)
    }

    override suspend fun getRandomMeal(): RecipeResponse {
        return services.getRandomMeal()
    }

    override suspend fun getAllCategories(): CategoryResponse {
        return services.getAllCategories()
    }

    override suspend fun getAllAreas(): AreaResponse {
        return services.getAllAreas()
    }

    override suspend fun getAllIngredients(): IngredientResponse {
        return services.getAllIngredients()
    }

    override suspend fun getCategoryMeals(category: String): RecipeResponse {
        return services.getCategoryMeals(category)
    }

    override suspend fun getIngredientMeals(ingredient: String): RecipeResponse {
        return services.getIngredientMeals(ingredient)
    }

    override suspend fun getAreaMeals(area: String): RecipeResponse {
        return services.getAreaMeals(area)
    }

    override fun getIngredientsAndItsMeasures(meal: Meal): ArrayList<Pair<String, String>> {
        return services.getIngredientsAndItsMeasures(meal)
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

    fun getIngredientsAndItsMeasures(meal: Meal): ArrayList<Pair<String, String>>
}