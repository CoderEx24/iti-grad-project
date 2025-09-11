package com.example.iti_grad_project.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iti_grad_project.R
import com.example.iti_grad_project.data.local.FavoriteRecipe
import com.example.iti_grad_project.ui.adapters.FavouriteAdapter
import com.example.iti_grad_project.ui.viewmodels.FavouriteViewModel
import com.example.iti_grad_project.utils.onRemoveClick
import com.example.iti_grad_project.utils.onShowMoreClick
import com.google.android.material.button.MaterialButton

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
                showDialog(recipe)

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


    private fun showDialog(recipe: FavoriteRecipe) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.confirmation_dialog)

        val message: TextView = dialog.findViewById(R.id.dialogMessage)
        message.text = "Remove ${recipe.strMeal} "


        val yesBtn: MaterialButton = dialog.findViewById(R.id.btnConfirm)
        yesBtn.setOnClickListener {
            onRemoveClick(this, recipe)
            dialog.dismiss()
        }

        val noBtn: Button = dialog.findViewById(R.id.btnCancel)
        noBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

        // 🔥 Force dialog width to match parent
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}