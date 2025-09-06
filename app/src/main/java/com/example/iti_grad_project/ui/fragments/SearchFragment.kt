package com.example.iti_grad_project.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.example.iti_grad_project.R
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.iti_grad_project.ui.adapters.RecipesAdapter
import com.example.iti_grad_project.utils.onShowMoreClick
import com.google.android.material.transition.MaterialSharedAxis

class SearchFragment : Fragment() {

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
            onShowMoreClick(this, recipe)
        }
        rvSearchResult.adapter = recipeAdapter

        if(!searchBar.text.isEmpty()) recipeAdapter.updateData(listOf())
        //endregion

    }
}