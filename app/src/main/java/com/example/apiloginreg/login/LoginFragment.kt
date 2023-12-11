package com.example.apiloginreg.login

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.apiloginreg.R
import com.example.apiloginreg.api.AuthRepository
import com.example.apiloginreg.api.AuthResult
import com.example.apiloginreg.api.AuthViewModel
import com.example.apiloginreg.api.AuthViewModelFactory
import com.example.apiloginreg.api.RetrofitInstance
import com.example.apiloginreg.base.BaseFragment
import com.example.apiloginreg.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(AuthRepository(RetrofitInstance.apiService))
    }

    override fun start() {

    }

    override fun clickListener() {
        binding.apply {

            btnLogin.setOnClickListener {
                authViewModel.loginUser(edEmail.text.toString(), edPass.text.toString())
                observe()
            }

            tvGoToReg.setOnClickListener {
                findNavController().navigate(
                    R.id.action_loginFragment_to_regFragment
                )
            }
        }
    }

//    private fun isValidEmail(email: String): Boolean {
//        val regex = Regex("eve\\.holt@reqres\\.in", RegexOption.IGNORE_CASE)
//        return regex.matches(email)
//    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.loginResult.collect { result ->
                    result?.let {
                        when (result) {
                            is AuthResult.Success -> {
                                // Success login
                                val token = result.token
                                Toast.makeText(requireContext(), token, Toast.LENGTH_SHORT).show()
//                                findNavController().navigate(
//                                    R.id.action_loginFragment_to_homeFragment
//                                )
                            }
                            is AuthResult.Error -> {
                                // Error message
                                val errorMessage = result.errorMessage
                                Log.e("errorLoginError", "$errorMessage")
                                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT)
                                    .show()
                            }

                            else -> {
                                Toast.makeText(requireContext(), "else", Toast.LENGTH_SHORT)
                                .show()
                            }
                        }
                    }
                }
            }
        }
    }

}