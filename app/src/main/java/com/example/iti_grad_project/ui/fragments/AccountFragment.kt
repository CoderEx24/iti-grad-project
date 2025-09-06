package com.example.iti_grad_project.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.example.iti_grad_project.R
import com.example.iti_grad_project.data.prefs.PreferenceManager
import com.example.iti_grad_project.ui.activities.AuthActivity

class AccountFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefManager = PreferenceManager(requireContext())

        val usernameTextView: TextView = view.findViewById(R.id.tvUserName)
        val about = view.findViewById<LinearLayout>(R.id.btnAboutApp)
        val signOut = view.findViewById<LinearLayout>(R.id.btnSignOut)

        usernameTextView.text = prefManager.getUsername()

        about.setOnClickListener {
            findNavController().navigate(R.id.aboutFragment)
        }

        signOut.setOnClickListener {
            prefManager.setLoggedIn(false)
            prefManager.setUsername("")

            val intent = Intent(requireContext(), AuthActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}