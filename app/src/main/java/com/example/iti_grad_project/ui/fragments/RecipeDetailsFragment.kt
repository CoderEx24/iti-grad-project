package com.example.iti_grad_project.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iti_grad_project.R
import com.example.iti_grad_project.data.local.FavoriteRecipe
import com.example.iti_grad_project.data.prefs.PreferenceManager
import com.example.iti_grad_project.data.remote.Meal
import com.example.iti_grad_project.ui.adapters.IngredientsAdapter
import com.example.iti_grad_project.ui.viewmodels.FavouriteViewModel
import com.example.iti_grad_project.ui.viewmodels.RecipeDetailsViewModel
import kotlinx.coroutines.launch

class RecipeDetailsFragment : Fragment() {

    lateinit var viewModel: RecipeDetailsViewModel

    lateinit var favViewModel: FavouriteViewModel

    // Views as lateinit properties
    private lateinit var ivRecipe: ImageView
    private lateinit var ivPlayButton: ImageView
    private lateinit var tvMealName: TextView
    private lateinit var tvCategoryArea: TextView
    private lateinit var tvInstructions: TextView
    private lateinit var btnAddToFav: ImageView
    private lateinit var wvVideo: WebView
    private lateinit var rvIngredients: RecyclerView
    private lateinit var ingredientAdapter: IngredientsAdapter

    private lateinit var prefs: PreferenceManager

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

        favViewModel = ViewModelProvider.create(
            this as ViewModelStoreOwner,
            factory = FavouriteViewModel.Factory,
            extras = MutableCreationExtras().apply {
                set(FavouriteViewModel.CONTEXT_KEY, requireContext())
            }
        )[FavouriteViewModel::class]

        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mealId = arguments?.getString("meal")

        // Initialize all views
        ivRecipe = view.findViewById(R.id.ivRecipeImage)
        ivPlayButton = view.findViewById(R.id.ivPlayButton)
        tvMealName = view.findViewById(R.id.tvMealName)
        tvCategoryArea = view.findViewById(R.id.tvCategoryArea)
        tvInstructions = view.findViewById(R.id.tvInstructions)
        btnAddToFav = view.findViewById(R.id.ivFavorite)
        wvVideo = view.findViewById(R.id.wvYouTube)
        rvIngredients = view.findViewById(R.id.rvIngredients)

        // Initialize preferences
        prefs = PreferenceManager(requireContext())

        ingredientAdapter = IngredientsAdapter(listOf())
        rvIngredients.adapter = ingredientAdapter

        viewModel.mealDetails.observe(viewLifecycleOwner) { newData ->
            updateData(newData.meal)
            ingredientAdapter.updateData(newData.ingredientsAndMeasurements)
        }

        val displayMetrics = resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density

        val columnWidthDp = 160 // desired width per item in dp
        val spanCount = (screenWidthDp / columnWidthDp).toInt().coerceAtLeast(2)

        rvIngredients.layoutManager = GridLayoutManager(requireContext(), spanCount)

        if(mealId == null) throw IllegalArgumentException("No meal to show")

        viewModel.fetchDetails(mealId)

    }

    private fun updateData(meal: Meal) {
        try {
            // Title
            tvMealName.text = meal.strMeal

            // Image
            Glide.with(requireContext())
                .load(meal.strMealThumb)
                .placeholder(R.drawable.ic_account_placeholder)
                .error(R.drawable.missing_image)
                .centerCrop()
                .into(ivRecipe)

            // Category and Area
            tvCategoryArea.text = "${meal.strCategory} - ${meal.strArea}"

            // Instructions
            tvInstructions.text = meal.strInstructions

            // Youtube video
            meal.strYoutube?.let { youtubeUrl ->
                val videoId = youtubeUrl.substringAfter("v=")
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

            // Handle favourites only if user is logged in
            val username = prefs.getUsername() ?: ""

            Log.i("USERNAME", "onViewCreated: $username")
            if (username.isEmpty()) {
                btnAddToFav.setOnClickListener {
                    Toast.makeText(requireContext(), getString(R.string.login_required), Toast.LENGTH_SHORT).show()
                }
                return
            }


            val recipe = FavoriteRecipe(meal.idMeal, username, meal.strMeal, meal.strMealThumb)

            lifecycleScope.launch {
                var isFavorite = favViewModel.isFavourite(recipe)

                Log.i("IS_FAVORITE", "onViewCreated: $isFavorite")

                if(isFavorite)
                    btnAddToFav.imageTintList = null


                btnAddToFav.setOnClickListener {
                    isFavorite = !isFavorite
                    if (isFavorite) {
                        btnAddToFav.imageTintList = null
                        favViewModel.addFavourite(recipe)
                    } else {
                        btnAddToFav.imageTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(requireContext(), R.color.light_gray)
                        )
                        favViewModel.removeFavourite(recipe)
                    }
                }
            }

        } catch (e: IllegalArgumentException) {
            Log.i("ERROR", "onViewCreated: ${e.message}")
        }
    }
}
