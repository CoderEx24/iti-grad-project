package com.example.iti_grad_project.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.iti_grad_project.R
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.iti_grad_project.ui.adapters.RecipesAdapter
import com.example.iti_grad_project.ui.viewmodels.SearchViewModel
import com.example.iti_grad_project.utils.onShowMoreClick
import com.google.android.material.transition.MaterialSharedAxis

class SearchFragment : Fragment() {
    lateinit var viewModel: SearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val forward = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        val backward = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)

        //Google's recommended way to handle transitions
        enterTransition = forward
        returnTransition = backward
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider.create(
            this as ViewModelStoreOwner,
            factory = SearchViewModel.Factory,
            extras = MutableCreationExtras().apply {
                set(SearchViewModel.CONTEXT_KEY, requireContext())
            }
        )[SearchViewModel::class]
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //region initialize
        val searchBar = view.findViewById<EditText>(R.id.et_search)

        // Request focus and open keyboard
        //This is so when we click on the search bar in home fragment
        //We go to search fragment with the search bar in focus.
        searchBar.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(searchBar, InputMethodManager.SHOW_IMPLICIT)
        //endregion

        //region perform query
        val rvSearchResult = view.findViewById<RecyclerView>(R.id.rv_search_results)

        val recipeAdapter = RecipesAdapter(listOf()) { recipe ->
            onShowMoreClick(this, recipe.idMeal)
        }
        rvSearchResult.adapter = recipeAdapter

        if(!searchBar.text.isEmpty()) recipeAdapter.updateData(listOf())

        var etCategories = view.findViewById<AutoCompleteTextView>(R.id.et_category)
        var etCountry = view.findViewById<AutoCompleteTextView>(R.id.et_country)
        var etIngredient = view.findViewById<AutoCompleteTextView>(R.id.et_ingredient)

        initializeObservers(etCategories, etCountry, etIngredient, recipeAdapter)

        //region onClickListeners
        viewModel.getAllFilters()

        etCategories.setOnItemClickListener{ parent, _, position, id ->
            val selectedValue = parent.getItemAtPosition(position).toString()

            etCountry.clearListSelection()
            etIngredient.clearListSelection()
            viewModel.categorySearch(selectedValue)
        }
        etCountry.setOnItemClickListener{ parent, _, position, id ->
            val selectedValue = parent.getItemAtPosition(position).toString()

            etIngredient.clearListSelection()
            etCategories.clearListSelection()
            viewModel.countrySearch(selectedValue)
        }
        etIngredient.setOnItemClickListener{ parent, _, position, id ->
            val selectedValue = parent.getItemAtPosition(position).toString()

            etCountry.clearListSelection()
            etCategories.clearListSelection()
            viewModel.ingredientSearch(selectedValue)
        }
        //endregion

        searchBar.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {

                // Call your custom function with the entered text
                viewModel.performSearchQuery(searchBar.text.toString())
                true // consume the event
            } else {
                false
            }
        }

    }
    private fun initializeObservers(etCategories: AutoCompleteTextView, etCountry: AutoCompleteTextView, etIngredient: AutoCompleteTextView, recipeAdapter: RecipesAdapter)
    {
        viewModel.filtersData.observe(viewLifecycleOwner)
        { filtersUiState ->
            populateFilters(etCategories, etCountry, etIngredient)
        }

        viewModel.apiData.observe(viewLifecycleOwner)
        { searchUiState ->
            recipeAdapter.updateData(searchUiState.searchResult)
        }
    }

    private fun populateFilters(categoriesView: AutoCompleteTextView, countriesView: AutoCompleteTextView, ingredientsView: AutoCompleteTextView,)
    {
        //region adapters
        val categoriesAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            viewModel.filtersData.value?.categoryResponse?.meals!!.map { it.strCategory })
        val ingredientsAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            viewModel.filtersData.value?.ingredientResponse?.meals!!.map { it.strIngredient })

        val countriesAdapter = ArrayAdapter(
        requireContext(),
        android.R.layout.simple_dropdown_item_1line,
            viewModel.filtersData.value?.countryResponse?.meals!!.map { it.strArea })

        //endregion
        categoriesView.setAdapter(categoriesAdapter)
        countriesView.setAdapter(countriesAdapter)
        ingredientsView.setAdapter(ingredientsAdapter)
    }
}