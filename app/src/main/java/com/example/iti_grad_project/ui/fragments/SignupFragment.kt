package com.example.iti_grad_project.ui.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.example.iti_grad_project.R
import com.example.iti_grad_project.ui.viewmodels.AuthViewModel

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val usernameTextView: TextView = view.findViewById(R.id.fragment_signup_username)
        val passwordTextView: TextView = view.findViewById(R.id.fragment_signup_password)
        val confirmPasswordTextView: TextView = view.findViewById(R.id.fragment_signup_confirmPassword)

        usernameTextView.addTextChangedListener {
            authViewModel.username = it?.toString() ?: ""
        }

        passwordTextView.addTextChangedListener {
            authViewModel.password = it?.toString() ?: ""
        }

        val passwordsMatchLambda: (Editable?) -> Unit = {
                val password = passwordTextView.text
                val confirmPassword = confirmPasswordTextView.text

                if (!password.isEmpty() && !confirmPassword.isEmpty() &&
                    !password.contentEquals(confirmPassword)) {
                    confirmPasswordTextView.error = "Passwords don't match"
                } else {
                    confirmPasswordTextView.error = null
                }
        }

        passwordTextView.addTextChangedListener(afterTextChanged = passwordsMatchLambda)
        confirmPasswordTextView.addTextChangedListener(afterTextChanged = passwordsMatchLambda)
    }
}