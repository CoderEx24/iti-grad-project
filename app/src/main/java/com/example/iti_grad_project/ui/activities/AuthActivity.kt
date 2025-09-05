package com.example.iti_grad_project.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.MutableCreationExtras
import com.example.iti_grad_project.R
import androidx.navigation.findNavController
import com.example.iti_grad_project.ui.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

class AuthActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        authViewModel = ViewModelProvider.create(
            this as ViewModelStoreOwner,
            factory = AuthViewModel.Factory,
            extras = MutableCreationExtras().apply {
                set(AuthViewModel.CONTEXT_KEY, this@AuthActivity.applicationContext)
            }
        )[AuthViewModel::class]

        // TODO: This should happen elsewhere. At the Splash Screen activity for example
        if (authViewModel.isLoggedIn) goToRecipeActivity()
    }

    fun onGoToSignup(view: View?) {
        val navController =  findNavController(R.id.fragmentContainerView)
        navController.navigate(R.id.action_loginFragment_to_signupFragment)
    }

    fun onSignup(view: View?) {
        lifecycleScope.launch {
            val result = authViewModel.register()
            if (result) goToRecipeActivity()
            else Toast.makeText(this@AuthActivity, "Signup Failed", Toast.LENGTH_LONG).show()
        }
    }

    fun onLogin(view: View?) {
        lifecycleScope.launch {
            val result = authViewModel.login()
            if (result) goToRecipeActivity()
            else Toast.makeText(this@AuthActivity, "Login Failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun goToRecipeActivity() {
        val intent = Intent(this, RecipeActivity::class.java)
        startActivity(intent)
        finish()
    }
}