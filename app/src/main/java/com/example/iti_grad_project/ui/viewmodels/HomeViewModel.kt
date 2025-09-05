package com.example.iti_grad_project.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.iti_grad_project.data.remote.Meal
import com.example.iti_grad_project.repositories.RecipeRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.iti_grad_project.data.local.AppDatabase
import com.example.iti_grad_project.data.prefs.PreferenceManager
import com.example.iti_grad_project.data.remote.RecipeApi
import com.example.iti_grad_project.data.remote.RecipeApiImp
import com.example.iti_grad_project.data.remote.RecipeApiService
import com.example.iti_grad_project.data.remote.RecipeResponse
import com.example.iti_grad_project.repositories.AuthRepository
import kotlinx.coroutines.launch

data class HomeUiState(
    val recipeOfTheDay: Meal,
    val listOfRandomRecipes: List<Meal>
)

class HomeViewModel(var repo: RecipeRepository): ViewModel() {
    val apiData = MutableLiveData<HomeUiState>()
    lateinit private var singleMealResponse: RecipeResponse
    private var randomMeals = mutableListOf<Meal>()

    fun fetchRecipes(dayChanged: Boolean){
        viewModelScope.launch {
            try{
                if(dayChanged == true) {
                    singleMealResponse = repo.getRandomMeal()
                }
                for(i in 1..10) {
                    randomMeals.add(repo.getRandomMeal().meals[0])
                }
                apiData.postValue(HomeUiState(singleMealResponse.meals[0], randomMeals))
                Log.i("MEALS", "single meal: ${singleMealResponse}")
            }
            catch(e: Exception)
            {
                Log.i("ERROR", "fetchRecipes: ${e.message} ")
            }
        }
    }

    companion object {
        // TODO: See if there's a better way to do this
        val CONTEXT_KEY = object : CreationExtras.Key<Context> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = this[CONTEXT_KEY]
                HomeViewModel(
                    RecipeRepository(
                        RecipeApiService,
                        AppDatabase.getDatabase(context!!).recipeDao()
                    )
                )
            }
        }
    }
}