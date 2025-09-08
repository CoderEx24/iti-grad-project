package com.example.iti_grad_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isEmpty
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iti_grad_project.R
import com.example.iti_grad_project.data.local.FavoriteRecipe
import com.example.iti_grad_project.data.local.RecipeDao
import com.example.iti_grad_project.data.remote.RecipeApi
import com.example.iti_grad_project.data.remote.RecipeResponse
import com.example.iti_grad_project.repositories.RecipeRepository
import com.example.iti_grad_project.ui.adapters.FavouriteAdapter
import com.example.iti_grad_project.ui.adapters.RecipesAdapter
import com.example.iti_grad_project.ui.viewmodels.FavouriteViewModel
import com.example.iti_grad_project.ui.viewmodels.HomeViewModel
import com.example.iti_grad_project.utils.onRemoveClick
import com.example.iti_grad_project.utils.onShowMoreClick

class FavouriteFragment : Fragment() {
    lateinit var favoritesList: List<FavoriteRecipe>
    lateinit var rvFavorites: RecyclerView
    lateinit var emptyStateContainer: LinearLayout

    lateinit var viewModel: FavouriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider.create(
            this as ViewModelStoreOwner,
            factory = FavouriteViewModel.Factory,
            extras = MutableCreationExtras().apply {
                set(FavouriteViewModel.CONTEXT_KEY, requireContext())
            }
        )[FavouriteViewModel::class]

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


        val favoriteAdapter = FavouriteAdapter(favoritesList, { recipe ->
                onRemoveClick(this, recipe)

        },
            { recipeString -> onShowMoreClick(this, recipeString)

        })

        val displayMetrics = resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density

        val columnWidthDp = 160 // desired width per item in dp
        val spanCount = (screenWidthDp / columnWidthDp).toInt().coerceAtLeast(2)

        rvFavorites.adapter = favoriteAdapter
        rvFavorites.layoutManager = GridLayoutManager(requireContext(), spanCount)


//        rvFavorites.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.apiData.observe(viewLifecycleOwner) { state ->
            favoriteAdapter.updateData(state.listOfFavouriteRecipes)

            if (state.listOfFavouriteRecipes.isEmpty()) {
                rvFavorites.visibility = View.GONE
                emptyStateContainer.visibility = View.VISIBLE
            } else {
                rvFavorites.visibility = View.VISIBLE
                emptyStateContainer.visibility = View.GONE
            }
        }


        viewModel.fetchFavourites()
    }
}