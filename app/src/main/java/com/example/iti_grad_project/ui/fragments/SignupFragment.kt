package com.example.iti_grad_project.ui.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.text.TextWatcher
import com.example.iti_grad_project.R

class SignupFragment : Fragment(R.layout.fragment_signup) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val passwordTextView: TextView = view.findViewById(R.id.fragment_signup_password)
        val confirmPasswordTextView: TextView = view.findViewById(R.id.fragment_signup_confirmPassword)

        val passwordsMatchWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val password = passwordTextView.text
                val confirmPassword = confirmPasswordTextView.text

                if (!password.isEmpty() && !confirmPassword.isEmpty() &&
                    !password.contentEquals(confirmPassword)) {
                    confirmPasswordTextView.error = "Passwords don't match"
                } else {
                    confirmPasswordTextView.error = null
                }
            }

            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
            }
        }

        passwordTextView.addTextChangedListener(passwordsMatchWatcher)
        confirmPasswordTextView.addTextChangedListener(passwordsMatchWatcher)
    }

}