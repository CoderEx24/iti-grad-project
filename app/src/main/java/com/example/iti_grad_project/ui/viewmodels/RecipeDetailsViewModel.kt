package com.example.iti_grad_project.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.iti_grad_project.data.local.AppDatabase
import com.example.iti_grad_project.data.remote.Ingredient
import com.example.iti_grad_project.data.remote.Meal
import com.example.iti_grad_project.data.remote.RecipeApiService
import com.example.iti_grad_project.repositories.RecipeRepository
import com.example.iti_grad_project.utils.imageUrl
import kotlinx.coroutines.launch

data class ingredientDetails(
    val ingredientTitle: String,
    val measurement: String,
    val imageSrc: String
)
data class DetailsUiState(
    var ingredientsAndMeasurements: List<ingredientDetails>
)
class RecipeDetailsViewModel(val repo: RecipeRepository): ViewModel() {

    var mealDetails = MutableLiveData<DetailsUiState>()

    private var ingredients = mutableListOf<ingredientDetails>()
    fun fetchDetails(meal: Meal){
        viewModelScope.launch {
            try {
                val response = repo.getIngredientsAndItsMeasures(meal)
                for(i in response)
                {
                   ingredients.add(ingredientDetails(i.first, i.second, "$imageUrl${i.first}"))
                }
                mealDetails.postValue(DetailsUiState(ingredients))
            }
            catch(e: Exception){
                Log.i("ERROR", "ERROR at fetchDetails: ${e.message} ")
            }
        }
    }

    companion object {
        // TODO: See if there's a better way to do this
        val CONTEXT_KEY = object : CreationExtras.Key<Context> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = this[CONTEXT_KEY]
                RecipeDetailsViewModel(
                    RecipeRepository(
                        RecipeApiService,
                        AppDatabase.getDatabase(context!!).recipeDao()
                    )
                )
            }
        }
    }
}