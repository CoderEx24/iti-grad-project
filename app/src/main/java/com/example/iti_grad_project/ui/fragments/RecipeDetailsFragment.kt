package com.example.iti_grad_project.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.iti_grad_project.R
import com.example.iti_grad_project.data.remote.Meal

class RecipeDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val meal = arguments?.getParcelable<Meal>("meal")

        val tvMealName = view.findViewById<TextView>(R.id.tvMealName)
        try
        {
            if(meal == null) throw IllegalArgumentException("No meal to show")
            tvMealName.text = meal.strMeal
        }
        catch (e: IllegalArgumentException){
            Log.i("ERROR", "onViewCreated: ${e.message} ")
        }
    }
}