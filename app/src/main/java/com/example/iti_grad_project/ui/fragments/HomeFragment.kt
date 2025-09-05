package com.example.iti_grad_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iti_grad_project.R
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.iti_grad_project.data.remote.Meal
import com.example.iti_grad_project.ui.adapters.RecipesAdapter

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val testMeals = listOf(
            Meal(
                idMeal = "52918",
                strMeal = "Fish Stew with Rouille",
                strCategory = "Seafood",
                strArea = "French",
                strInstructions = "Twist the heads from the prawns, peel away the shells, fry, simmer...",
                strMealThumb = "https://www.themealdb.com/images/media/meals/vptqpw1511798500.jpg",
                strYoutube = "https://www.youtube.com/watch?v=fp9Lqx2EUco",
                strSource = "https://www.bbcgoodfood.com/recipes/532640/summer-fish-stew-with-rouille"
            ),
            Meal(
                idMeal = "52844",
                strMeal = "Lasagna",
                strCategory = "Pasta",
                strArea = "Italian",
                strInstructions = "Cook pasta sheets, layer with sauce and cheese, bake until golden...",
                strMealThumb = "https://www.themealdb.com/images/media/meals/wtsvxx1511296896.jpg",
                strYoutube = "https://www.youtube.com/watch?v=wTSVXX1511",
                strSource = "https://www.bbcgoodfood.com/recipes/classic-lasagna"
            ),
            Meal(
                idMeal = "52977",
                strMeal = "Tandoori Chicken",
                strCategory = "Chicken",
                strArea = "Indian",
                strInstructions = "Marinate chicken in yogurt and spices, then grill until charred...",
                strMealThumb = "https://www.themealdb.com/images/media/meals/qptpvt1487339892.jpg",
                strYoutube = "https://www.youtube.com/watch?v=qptpvt1487",
                strSource = "https://www.bbcgoodfood.com/recipes/tandoori-chicken"
            )
        )

        val rv_recipes = view.findViewById<RecyclerView>(R.id.rv_random_recipes)
        val recipeAdapter = RecipesAdapter(testMeals.toMutableList())
        rv_recipes.adapter = recipeAdapter

        val searchEditText = view.findViewById<EditText>(R.id.et_search)

        searchEditText.setOnClickListener {

            // Navigate to SearchFragment when clicked
            val navController = findNavController()
            navController.navigate(R.id.searchFragment)
        }
    }
}