package com.example.iti_grad_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iti_grad_project.R
import com.example.iti_grad_project.data.local.FavoriteRecipe
import com.example.iti_grad_project.data.local.RecipeDao
import com.example.iti_grad_project.data.remote.RecipeApi
import com.example.iti_grad_project.data.remote.RecipeResponse
import com.example.iti_grad_project.repositories.RecipeRepository

class FavouriteFragment : Fragment() {
    lateinit var favoritesList: List<FavoriteRecipe>
    lateinit var rvFavorites: RecyclerView
    lateinit var emptyStateContainer: LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get favorite recipes and insert into favoritesList

        favoritesList = listOf()

        rvFavorites = view.findViewById(R.id.rvFavoriteRecipes)
        emptyStateContainer = view.findViewById(R.id.emptyStateContainer)
        if (favoritesList.isEmpty()) {
            rvFavorites.visibility = View.GONE
            emptyStateContainer.visibility = View.VISIBLE
        } else {
            rvFavorites.visibility = View.VISIBLE
            emptyStateContainer.visibility = View.GONE
        }
    }
}