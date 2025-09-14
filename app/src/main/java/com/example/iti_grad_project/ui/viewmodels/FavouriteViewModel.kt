package com.example.iti_grad_project.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.iti_grad_project.data.local.AppDatabase
import com.example.iti_grad_project.data.local.FavoriteRecipe
import com.example.iti_grad_project.data.prefs.PreferenceManager
import com.example.iti_grad_project.data.remote.Meal
import com.example.iti_grad_project.data.remote.RecipeApiService
import com.example.iti_grad_project.data.remote.RecipeResponse
import com.example.iti_grad_project.repositories.AuthRepository
import com.example.iti_grad_project.repositories.RecipeRepository
import com.example.iti_grad_project.utils.ERROR_TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

data class FavouriteUiState(
    val listOfFavouriteRecipes: List<FavoriteRecipe>
)

class FavouriteViewModel(var recipeRepo: RecipeRepository, var authRepo: AuthRepository): ViewModel() {
    val apiData = MutableLiveData<FavouriteUiState>()

    fun fetchFavourites(){
        viewModelScope.launch {
            try{
                val response = recipeRepo.getAllFavourites(authRepo.getUserName()!!)
                apiData.postValue(FavouriteUiState(response))

                Log.i("RESPONSE", "fetchRecipes: ${response} ")
            }
            catch(e: Exception)
            {
                Log.i(ERROR_TAG, "fetchRecipes: ${e.message} ")
            }
        }
    }

    fun addFavourite(recipe: FavoriteRecipe){
        viewModelScope.launch {
            try{
                recipeRepo.addFavourite(recipe)

            }
            catch(e: Exception)
            {
                Log.i(ERROR_TAG, "addFavourite: ${e.message} ")
            }
        }
    }

    fun removeFavourite(recipe: FavoriteRecipe){
        viewModelScope.launch {
            try {
                recipeRepo.deleteFavourite(recipe)
                fetchFavourites()
            } catch (e: Exception) {
                Log.i(ERROR_TAG, "removeFavourite: ${e.message}")
            }
        }
    }


    suspend fun isFavourite(recipe: FavoriteRecipe): Boolean {
        return try {
            val response = recipeRepo.isFavourite(authRepo.getUserName()!!, recipe.idMeal)
            Log.i("RESPONSE", "isFavourite: $response")
            response != null
        } catch (e: Exception) {
            Log.i(ERROR_TAG, "isFavourite: ${e.message}")
            false
        }
    }

    companion object {
        val CONTEXT_KEY = object : CreationExtras.Key<Context> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = this[CONTEXT_KEY]
                FavouriteViewModel(
                    RecipeRepository(
                        RecipeApiService,
                        AppDatabase.getDatabase(context!!).recipeDao()
                    ), AuthRepository(
                        AppDatabase.getDatabase(context!!).userDao(),
                        PreferenceManager(context!!)
                    )
                )
            }
        }
    }
}