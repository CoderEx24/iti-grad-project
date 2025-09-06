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
import com.example.iti_grad_project.data.remote.AreaResponse
import com.example.iti_grad_project.data.remote.CategoryResponse
import com.example.iti_grad_project.data.remote.IngredientResponse
import com.example.iti_grad_project.data.remote.Meal
import com.example.iti_grad_project.data.remote.RecipeApiService
import com.example.iti_grad_project.repositories.RecipeRepository
import kotlinx.coroutines.launch

data class SearchUiState(
    val searchResult: List<Meal>,

)
data class FiltersUiState(
    val categoryResponse: CategoryResponse,
    val ingredientResponse: IngredientResponse,
    val countryResponse: AreaResponse
)
class SearchViewModel(var repo: RecipeRepository): ViewModel() {

    val apiData = MutableLiveData<SearchUiState>()
    val filtersData = MutableLiveData<FiltersUiState>()

    lateinit var searchResponse: List<Meal>

    //region filters
    suspend fun getCategories(): CategoryResponse {
        return try {
            val response = repo.getAllCategories()
            response
        } catch (e: Exception) {
            Log.i("ERROR", "fetchRecipes: ${e.message}")
            CategoryResponse(emptyList())
        }
    }


    suspend fun getIngredients(): IngredientResponse{
            return try {
                val response = repo.getAllIngredients()
                response
            } catch (e: Exception) {
                Log.i("ERROR", "fetchRecipes: ${e.message} ")
                IngredientResponse(emptyList())
            }

    }

    suspend fun getCountries(): AreaResponse{
            return try {
                val response = repo.getAllAreas()
                response
            } catch (e: Exception) {
                Log.i("ERROR", "fetchRecipes: ${e.message} ")
                AreaResponse(emptyList())
            }
    }


    fun getAllFilters(){
        viewModelScope.launch {
            try {
                val filtersUiState = FiltersUiState(getCategories(),
                                                    getIngredients(),
                                                    getCountries())

                filtersData.postValue(filtersUiState)
            } catch (e: Exception) {
                Log.i("ERROR", "fetchRecipes: ${e.message} ")
            }
        }
    }

    //endregion

    //region queries

    fun categorySearch(category: String){
        viewModelScope.launch {
            try {
                searchResponse = repo.getCategoryMeals(category).meals
                apiData.postValue(SearchUiState(searchResponse))
            }
            catch(e: Exception)
            {
                Log.i("ERROR", "fetchRecipes: ${e.message} ")
            }
        }
    }

    fun ingredientSearch(ingredient: String){
        viewModelScope.launch {
            try {
                searchResponse = repo.getIngredientMeals(ingredient).meals
                apiData.postValue(SearchUiState(searchResponse))
            }
            catch(e: Exception)
            {
                Log.i("ERROR", "fetchRecipes: ${e.message} ")
            }
        }
    }
    fun countrySearch(country: String){
        viewModelScope.launch {
            try {
                searchResponse = repo.getAreaMeals(country).meals
                apiData.postValue(SearchUiState(searchResponse))
            }
            catch(e: Exception)
            {
                Log.i("ERROR", "fetchRecipes: ${e.message} ")
            }
        }
    }

    fun performSearchQuery(query: String){
        viewModelScope.launch {
            try {
                searchResponse = repo.searchByName(query).meals
                apiData.postValue(SearchUiState(searchResponse))
            }
            catch(e: Exception)
            {
                Log.i("ERROR", "fetchRecipes: ${e.message} ")
            }
        }
    }

    //endregion
    companion object {
        // TODO: See if there's a better way to do this
        val CONTEXT_KEY = object : CreationExtras.Key<Context> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = this[CONTEXT_KEY]
                SearchViewModel(
                    RecipeRepository(
                        RecipeApiService,
                        AppDatabase.getDatabase(context!!).recipeDao()
                    )
                )
            }
        }
    }
}