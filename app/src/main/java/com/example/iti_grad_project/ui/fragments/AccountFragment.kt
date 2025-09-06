package com.example.iti_grad_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.example.iti_grad_project.R

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
        var about = view.findViewById<LinearLayout>(R.id.btnAboutApp)
        var signOut = view.findViewById<LinearLayout>(R.id.btnSignOut)

        about.setOnClickListener {
            findNavController().navigate(R.id.aboutFragment)
        }
        signOut.setOnClickListener {
            Toast.makeText(requireContext(), "Signing out...", Toast.LENGTH_SHORT).show()
            //Signout api call
        }
    }
}