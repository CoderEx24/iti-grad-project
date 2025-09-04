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
import androidx.lifecycle.viewmodel.MutableCreationExtras
import com.example.iti_grad_project.R
import androidx.navigation.findNavController
import com.example.iti_grad_project.ui.viewmodels.AuthViewModel

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
    }

    fun onGoToSignup(view: View?) {
        val navController =  findNavController(R.id.fragmentContainerView)
        navController.navigate(R.id.action_loginFragment_to_signupFragment)
    }
}