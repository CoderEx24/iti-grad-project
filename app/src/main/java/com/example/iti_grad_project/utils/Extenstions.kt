package com.example.iti_grad_project.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.iti_grad_project.R
import com.example.iti_grad_project.data.remote.Meal

fun onShowMoreClick(fragment: Fragment, recipe: Meal)
{
    val bundle = Bundle()
    bundle.putParcelable("meal", recipe)
    findNavController(fragment).navigate(R.id.recipeDetailsFragment, bundle)
}