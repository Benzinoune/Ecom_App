package com.example.ecomapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.abdo.ecomapp.R
import com.example.ecomapp.activities.LoginActivity
import com.example.ecomapp.utils.PreferenceHelper

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        val emailTextView = view.findViewById<TextView>(R.id.emailTextView)
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)

        val user = PreferenceHelper.getLoggedInUser(requireContext())
        nameTextView.text = user?.name ?: ""
        emailTextView.text = user?.email ?: ""

        logoutButton.setOnClickListener {
            PreferenceHelper.logout(requireContext())
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
    }
}

