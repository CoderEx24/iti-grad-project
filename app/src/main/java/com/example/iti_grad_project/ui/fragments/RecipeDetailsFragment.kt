package com.example.iti_grad_project.ui.fragments

import android.os.Bundle
import android.transition.Visibility
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.observe
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iti_grad_project.R
import com.example.iti_grad_project.data.remote.Meal
import com.example.iti_grad_project.ui.adapters.IngredientsAdapter
import com.example.iti_grad_project.ui.viewmodels.HomeViewModel
import com.example.iti_grad_project.ui.viewmodels.RecipeDetailsViewModel

class RecipeDetailsFragment : Fragment() {
    lateinit var viewModel: RecipeDetailsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider.create(
            this as ViewModelStoreOwner,
            factory = RecipeDetailsViewModel.Factory,
            extras = MutableCreationExtras().apply {
                set(RecipeDetailsViewModel.CONTEXT_KEY, requireContext())
            }
        )[RecipeDetailsViewModel::class]

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val meal = arguments?.getParcelable<Meal>("meal")

        val ivRecipe = view.findViewById<ImageView>(R.id.ivRecipeImage)
        val ivPlayButton = view.findViewById<ImageView>(R.id.ivPlayButton)
        val tvMealName = view.findViewById<TextView>(R.id.tvMealName)
        val tvCategoryArea = view.findViewById<TextView>(R.id.tvCategoryArea)
        val tvInstructions = view.findViewById<TextView>(R.id.tvInstructions)

        var btnAddToFav = view.findViewById<ImageButton>(R.id.btnFavorite)

        var wvVideo = view.findViewById<WebView>(R.id.wvYouTube)

        val rvIngredients = view.findViewById<RecyclerView>(R.id.rvIngredients)
        val ingredientAdapter = IngredientsAdapter(listOf())
        rvIngredients.adapter = ingredientAdapter

        viewModel.mealDetails.observe(viewLifecycleOwner){ newData ->
            ingredientAdapter.updateData(newData.ingredientsAndMeasurements)
        }

        try
        {
            if(meal == null) throw IllegalArgumentException("No meal to show")

            //Title
            tvMealName.text = meal.strMeal
            //Image
            Glide.with(requireContext())
                .load(meal.strMealThumb)
                .placeholder(R.drawable.ic_account_placeholder)
                .error(R.drawable.missing_image)
                .centerCrop()
                .into(ivRecipe)

            //Category and Area
            tvCategoryArea.text = "${meal.strCategory} - ${meal.strArea}"

            //Instructions
            tvInstructions.text = meal.strInstructions

            //Youtube video
            meal?.strYoutube?.let { youtubeUrl ->
                val videoId = youtubeUrl.substringAfter("v=") // Extract the video ID
                val embedHtml = """
                                    <html>
                                    <body style="margin:0">
                                    <iframe width="100%" height="100%" 
                                        src="https://www.youtube.com/embed/$videoId?autoplay=0&modestbranding=1&controls=1" 
                                        frameborder="0" allowfullscreen>
                                    </iframe>
                                    </body>
                                    </html>
                                """
                wvVideo.settings.javaScriptEnabled = true
                wvVideo.settings.loadWithOverviewMode = true
                wvVideo.settings.useWideViewPort = true
                wvVideo.webViewClient = WebViewClient()
                wvVideo.loadData(embedHtml, "text/html", "utf-8")
                ivPlayButton.visibility = GONE
            }

            btnAddToFav.setOnClickListener {
                Toast.makeText(requireContext(), "SOON TO BE IMPLEMENTED", Toast.LENGTH_SHORT).show()
                //Add to favourites
            }
            viewModel.fetchDetails(meal)
        }
        catch (e: IllegalArgumentException){
            Log.i("ERROR", "onViewCreated: ${e.message} ")
        }
    }
}