package com.example.iti_grad_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.iti_grad_project.R
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iti_grad_project.data.remote.Meal
import com.example.iti_grad_project.ui.adapters.RecipesAdapter
import com.example.iti_grad_project.ui.viewmodels.AuthViewModel
import com.example.iti_grad_project.ui.viewmodels.HomeViewModel
import com.example.iti_grad_project.utils.onShowMoreClick

class HomeFragment : Fragment() {
    lateinit var viewModel: HomeViewModel

    lateinit var rv_recipes: RecyclerView
    lateinit var recipeOfTheDayView: View
    lateinit var tvRecipeName: TextView
    lateinit var ivRecipeImage: ImageView

    lateinit var btnShowMore: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        viewModel = ViewModelProvider.create(
            this as ViewModelStoreOwner,
            factory = HomeViewModel.Factory,
            extras = MutableCreationExtras().apply {
                set(HomeViewModel.CONTEXT_KEY, requireContext())
            }
        )[HomeViewModel::class]

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rv_recipes = view.findViewById(R.id.rv_random_recipes)
        recipeOfTheDayView = view.findViewById(R.id.recipe_of_the_day_card)

        tvRecipeName = recipeOfTheDayView.findViewById(R.id.tvRecipeTitle)
        ivRecipeImage = recipeOfTheDayView.findViewById(R.id.ivRecipeImage)
        btnShowMore = recipeOfTheDayView.findViewById(R.id.btnShowMore)
        val recipeAdapter = RecipesAdapter(listOf())
        { recipe ->
            onShowMoreClick(this, recipe.idMeal)
        }
        rv_recipes.adapter = recipeAdapter

        val searchEditText = view.findViewById<EditText>(R.id.et_search)

        searchEditText.setOnClickListener {

            // Navigate to SearchFragment when clicked
            findNavController().navigate(R.id.searchFragment)
        }
        viewModel.apiData.observe(viewLifecycleOwner) { meal ->
            //Populate meal of the day
            updateMealOfTheDay(meal.recipeOfTheDay)
            //Populate
            recipeAdapter.updateData(meal.listOfRandomRecipes)
        }

        viewModel.fetchRecipes(true)
    }
    fun updateMealOfTheDay(meal: Meal, dayChanged: Boolean = false){
        //if(!dayChanged) return
        tvRecipeName.text = meal.strMeal
        Glide.with(requireContext())
            .load(meal.strMealThumb)
            .placeholder(R.drawable.ic_account_placeholder)
            .error(R.drawable.missing_image)
            .centerCrop()
            .into(ivRecipeImage)

        btnShowMore.setOnClickListener {
            onShowMoreClick(this, meal.idMeal)
        }
    }
}