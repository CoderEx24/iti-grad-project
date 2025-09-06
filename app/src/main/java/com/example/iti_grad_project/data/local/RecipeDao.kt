package com.example.iti_grad_project.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(recipe: FavoriteRecipe)

    @Delete
    suspend fun deleteFavorite(recipe: FavoriteRecipe)

    @Query("SELECT * FROM favorite_recipes WHERE username = :username")
    suspend fun getAllFavorites(username: String): List<FavoriteRecipe>

    @Query("SELECT * FROM favorite_recipes WHERE username = :username AND idMeal = :idMeal")
    suspend fun isFavourite(username: String, idMeal: String): FavoriteRecipe?

}