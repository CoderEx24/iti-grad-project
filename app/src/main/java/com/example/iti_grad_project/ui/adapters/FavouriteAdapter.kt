package com.example.iti_grad_project.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iti_grad_project.R
import com.example.iti_grad_project.data.local.FavoriteRecipe
import com.example.iti_grad_project.data.remote.Meal

class FavouriteAdapter(
    private var data: List<FavoriteRecipe>,
    private val onRemoveClick: (FavoriteRecipe) -> Unit

) : RecyclerView.Adapter<FavouriteAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.favourite_item, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recipe = data[position]

        // Set title
        holder.tvRecipeTitle.text = recipe.strMeal

        // Load image with Glide
        Glide.with(holder.itemView.context)
            .load(recipe.strMealThumb)
            .placeholder(R.drawable.ic_account_placeholder)
            .error(R.drawable.missing_image)
            .centerCrop()
            .into(holder.ivRecipeImage)


        holder.btnRemove.setOnClickListener {
            onRemoveClick(recipe)
        }

        // Handle Show More button click
//        holder.btnShowMore.setOnClickListener {
//            onShowMoreClick(recipe)
//        }
        Log.i("RECIPESEARCH", "onBindViewHolder: ${recipe} ")
    }

    override fun getItemCount() = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<FavoriteRecipe>) {
        data = newData
        notifyDataSetChanged()
    }

    class MyViewHolder(row: View) : RecyclerView.ViewHolder(row) {

        val ivRecipeImage: ImageView = itemView.findViewById(R.id.ivRecipeImage)
        val tvRecipeTitle: TextView = itemView.findViewById(R.id.tvRecipeTitle)

        val btnRemove: ImageButton = itemView.findViewById(R.id.btnRemove)
//        val btnShowMore: Button = itemView.findViewById(R.id.btnShowMore)
    }
}