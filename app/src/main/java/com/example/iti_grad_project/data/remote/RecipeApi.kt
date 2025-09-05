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

    fun getIngredientsAndItsMeasures(meal: Meal): ArrayList<Pair<String, String>> {
        val list = ArrayList<Pair<String, String>>()

        val ingredients = listOf(
            meal.strIngredient1, meal.strIngredient2, meal.strIngredient3, meal.strIngredient4,
            meal.strIngredient5, meal.strIngredient6, meal.strIngredient7, meal.strIngredient8,
            meal.strIngredient9, meal.strIngredient10, meal.strIngredient11, meal.strIngredient12,
            meal.strIngredient13, meal.strIngredient14, meal.strIngredient15, meal.strIngredient16,
            meal.strIngredient17, meal.strIngredient18, meal.strIngredient19, meal.strIngredient20
        )

        val measures = listOf(
            meal.strMeasure1, meal.strMeasure2, meal.strMeasure3, meal.strMeasure4,
            meal.strMeasure5, meal.strMeasure6, meal.strMeasure7, meal.strMeasure8,
            meal.strMeasure9, meal.strMeasure10, meal.strMeasure11, meal.strMeasure12,
            meal.strMeasure13, meal.strMeasure14, meal.strMeasure15, meal.strMeasure16,
            meal.strMeasure17, meal.strMeasure18, meal.strMeasure19, meal.strMeasure20
        )

        for (i in ingredients.indices) {
            val ingredient = ingredients[i]?.trim()
            val measure = measures[i]?.trim()

            if (!ingredient.isNullOrEmpty() && !measure.isNullOrEmpty()) {
                list.add(Pair(ingredient, measure))
            }
        }

        return list
    }

}