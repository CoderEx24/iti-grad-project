package com.example.iti_grad_project.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.iti_grad_project.R
import com.example.iti_grad_project.data.local.AppDatabase
import com.example.iti_grad_project.data.prefs.PreferenceManager
import com.example.iti_grad_project.ui.activities.AuthActivity
import com.example.iti_grad_project.ui.viewmodels.AuthViewModel
import com.example.iti_grad_project.ui.viewmodels.FavouriteViewModel
import com.example.iti_grad_project.utils.showDialog
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import kotlin.getValue

class AccountFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var profileImage: ImageView

    private val pickImage = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val path = saveImageToInternalStorage(it)
            Log.i("PATH", path)
            // 🔹 Save to DB via ViewModel
            try {
                authViewModel.updateProfileImage( path)
                Log.i("DONE", path)
            } catch (e: Exception) {
                Log.i("ERROR", "${e.message}")
            }

            Log.i("authViewModel", "Profile image updated")

            // 🔹 Update UI immediately
            try {
                Glide.with(this)
                    .load(path)
                    .placeholder(R.drawable.ic_user_placeholder)
                    .into(profileImage)
            } catch (e: Exception) {
                Log.i("ERROR", "${e.message}")
            }

            Log.i("UIUpdateImage", "Profile image updated")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        authViewModel = ViewModelProvider.create(
            this as ViewModelStoreOwner,
            factory = AuthViewModel.Factory,
            extras = MutableCreationExtras().apply {
                set(AuthViewModel.CONTEXT_KEY, requireContext())
            }
        )[AuthViewModel::class]

        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefManager = PreferenceManager(requireContext())

        val usernameTextView: TextView = view.findViewById(R.id.tvUserName)
        val about = view.findViewById<LinearLayout>(R.id.btnAboutApp)
        val signOut = view.findViewById<LinearLayout>(R.id.btnSignOut)
        profileImage = view.findViewById<ImageView>(R.id.profileImage)
        val changeProfile = view.findViewById<MaterialButton>(R.id.btnChangeProfile)

        usernameTextView.text = prefManager.getUsername()

        about.setOnClickListener {
            findNavController().navigate(R.id.aboutFragment)
        }

        signOut.setOnClickListener {
            showDialog(this, "Signing out", {prefManager.setLoggedIn(false)
                prefManager.setUsername("")

                val intent = Intent(requireContext(), AuthActivity::class.java)
                startActivity(intent)
                requireActivity().finish()},
                {})

        }

        lifecycleScope.launch {
            val imagePath = authViewModel.getProfileImage()
            if (!imagePath.isNullOrEmpty()) {
                Glide.with(this@AccountFragment)
                    .load(imagePath)
                    .placeholder(R.drawable.ic_user_placeholder)
                    .into(profileImage)
            }
        }


        changeProfile.setOnClickListener {
            pickImage.launch("image/*")
        }
    }

    private fun saveImageToInternalStorage(uri: Uri): String {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val file = File(requireContext().filesDir, "profile_${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        return file.absolutePath
    }
}