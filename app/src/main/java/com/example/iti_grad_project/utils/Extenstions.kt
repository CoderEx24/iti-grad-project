package com.example.iti_grad_project.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.iti_grad_project.R
import com.example.iti_grad_project.data.local.FavoriteRecipe
import com.example.iti_grad_project.data.prefs.PreferenceManager
import com.example.iti_grad_project.data.remote.Meal
import com.example.iti_grad_project.ui.fragments.FavouriteFragment

fun onShowMoreClick(fragment: Fragment, recipeId: String)
{
    val bundle = Bundle()
    bundle.putString("meal", recipeId)
    findNavController(fragment).navigate(R.id.recipeDetailsFragment, bundle)
}

fun onRemoveClick(fragment: FavouriteFragment, recipe: FavoriteRecipe) {
    fragment.viewModel.removeFavourite(recipe)
}

fun getEmptyMeal() = Meal("","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","")


fun PreferenceManager.shouldUpdateMeal(): Boolean {
    val lastUpdate = getRecipeOfTheDayTimestamp()
    val now = System.currentTimeMillis()
    val twentyFourHours = 24 * 60 * 60 * 1000L
    return (now - lastUpdate) >= twentyFourHours
}