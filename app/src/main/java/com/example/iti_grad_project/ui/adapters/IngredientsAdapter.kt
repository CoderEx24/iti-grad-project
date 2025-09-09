package com.example.iti_grad_project.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iti_grad_project.R
import com.example.iti_grad_project.data.remote.Meal
import com.example.iti_grad_project.ui.viewmodels.ingredientDetails

class IngredientsAdapter(
    private var data: List<ingredientDetails>,
) : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredient_card, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ingredientDetails = data[position]

        // Set title
        holder.tvIngredientTitle.text = ingredientDetails.ingredientTitle

        holder.tvMeasurement.text = ingredientDetails.measurement

        // Load image with Glide
        Glide.with(holder.itemView.context)
            .load(ingredientDetails.imageSrc)
            .placeholder(R.drawable.ic_meal_placeholder)
            .error(R.drawable.missing_image)
            .centerCrop()
            .into(holder.ivIngredientImage)


    }

    override fun getItemCount() = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<ingredientDetails>) {
        data = newData
        notifyDataSetChanged()
    }

    class MyViewHolder(row: View) : RecyclerView.ViewHolder(row) {

        val ivIngredientImage: ImageView = itemView.findViewById(R.id.ivIngredient)
        val tvIngredientTitle: TextView = itemView.findViewById(R.id.tvIngredientName)

        val tvMeasurement: TextView = itemView.findViewById(R.id.tvMeasurement)
    }
}

