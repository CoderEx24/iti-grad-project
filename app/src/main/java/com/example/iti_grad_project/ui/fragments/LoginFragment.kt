package com.example.iti_grad_project.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.iti_grad_project.R
import com.example.iti_grad_project.ui.viewmodels.AuthViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val loginButton: Button = view.findViewById(R.id.fragment_login_button_login)
        val usernameTextView: TextView = view.findViewById(R.id.fragment_login_username)
        val passwordTextView: TextView = view.findViewById(R.id.fragment_login_password)

        usernameTextView.addTextChangedListener {
            authViewModel.username = it?.toString() ?: ""
        }

        passwordTextView.addTextChangedListener {
            authViewModel.password = it?.toString() ?: ""
        }

        val enableLoginButtonLambda: (Editable?) -> Unit = {
            loginButton.isEnabled = usernameTextView.text.isNotBlank() && passwordTextView.text.isNotBlank()
        }

        usernameTextView.addTextChangedListener(afterTextChanged = enableLoginButtonLambda)
        passwordTextView.addTextChangedListener(afterTextChanged = enableLoginButtonLambda)
    }

}