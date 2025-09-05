package com.example.iti_grad_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iti_grad_project.R
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.iti_grad_project.data.remote.Meal
import com.example.iti_grad_project.ui.adapters.RecipesAdapter
import com.example.iti_grad_project.ui.viewmodels.AuthViewModel
import com.example.iti_grad_project.ui.viewmodels.HomeViewModel

class HomeFragment : Fragment() {
    lateinit var viewModel: HomeViewModel
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



        val rv_recipes = view.findViewById<RecyclerView>(R.id.rv_random_recipes)
        val recipeAdapter = RecipesAdapter(listOf())
        { recipe ->
            var bundle = Bundle()
            bundle.putSerializable()
            findNavController().navigate
        }
        rv_recipes.adapter = recipeAdapter



        val searchEditText = view.findViewById<EditText>(R.id.et_search)

        searchEditText.setOnClickListener {

            // Navigate to SearchFragment when clicked
            val navController = findNavController()
            navController.navigate(R.id.searchFragment)
        }
        viewModel.apiData.observe(viewLifecycleOwner) { meal ->
            recipeAdapter.updateData(meal.listOfRandomRecipes)
        }

        viewModel.fetchRecipes(true)
    }
}