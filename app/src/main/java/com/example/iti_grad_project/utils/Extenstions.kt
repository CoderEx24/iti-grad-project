package com.example.iti_grad_project.utils

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.iti_grad_project.R
import com.example.iti_grad_project.data.local.FavoriteRecipe
import com.example.iti_grad_project.data.prefs.PreferenceManager
import com.example.iti_grad_project.data.remote.Meal
import com.example.iti_grad_project.ui.fragments.FavouriteFragment
import com.google.android.material.button.MaterialButton

fun onShowMoreClick(fragment: Fragment, recipeId: String)
{
    val bundle = Bundle()
    bundle.putString("meal", recipeId)
    findNavController(fragment).navigate(R.id.recipeDetailsFragment, bundle)
}

fun onRemoveClick(fragment: FavouriteFragment, recipe: FavoriteRecipe) {
    fragment.viewModel.removeFavourite(recipe)
}


fun showDialog(
    fragment: Fragment,
    messageText: String,
    onConfirm: () -> Unit,
    onCancel: (() -> Unit)? = null // optional cancel
) {
    val dialog = Dialog(fragment.requireContext())
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setCanceledOnTouchOutside(true)
    dialog.setContentView(R.layout.confirmation_dialog)

    val message: TextView = dialog.findViewById(R.id.dialogMessage)
    message.text = messageText

    val yesBtn: MaterialButton = dialog.findViewById(R.id.btnConfirm)
    yesBtn.setOnClickListener {
        onConfirm()
        dialog.dismiss()
    }

    val noBtn: Button = dialog.findViewById(R.id.btnCancel)
    noBtn.setOnClickListener {
        onCancel?.invoke()
        dialog.dismiss()
    }

    dialog.show()

    // Force dialog width to match parent
    dialog.window?.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
}


fun getEmptyMeal() = Meal("","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","")


fun PreferenceManager.shouldUpdateMeal(): Boolean {
    val lastUpdate = getRecipeOfTheDayTimestamp()
    val now = System.currentTimeMillis()
    val twentyFourHours = 24 * 60 * 60 * 1000L
    return (now - lastUpdate) >= twentyFourHours
}