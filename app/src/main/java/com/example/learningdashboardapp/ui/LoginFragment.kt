package com.example.learningdashboardapp.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.learningdashboardapp.R
import com.example.learningdashboardapp.databinding.FragmentLoginBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {

        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()

        // Clear previous error
        binding.tvLoginError.visibility = View.GONE

        // Input validation
        if (email.isEmpty()) {
            showError("Please enter your email")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Please enter a valid email address")
            return
        }

        if (password.isEmpty()) {
            showError("Please enter your password")
            return
        }

        if (password.length < 6) {
            showError("Password must be at least 6 characters")
            return
        }

        // Loading state
        binding.progressBarLogin.visibility = View.VISIBLE
        binding.btnLogin.isEnabled = false

        viewLifecycleOwner.lifecycleScope.launch {

            // Simulate mock login request
            delay(500)

            if (!isAdded) {
                return@launch
            }

            // Mock login success
            binding.progressBarLogin.visibility = View.GONE
            binding.btnLogin.isEnabled = true

            findNavController().navigate(
                R.id.action_loginFragment_to_dashboardFragment
            )
        }
    }

    private fun showError(message: String) {

        binding.tvLoginError.text = message
        binding.tvLoginError.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}