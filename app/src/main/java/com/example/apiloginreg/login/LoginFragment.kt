package com.example.apiloginreg.login

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {

            authViewModel.loginResult.collect { result ->
                result?.let {
                    when (result) {
                        is AuthResult.Success -> {
                            // Success login
                            val token = result.token
                            Toast.makeText(requireContext(), token, Toast.LENGTH_SHORT).show()

                        }
                        is AuthResult.Error -> {
                            // Error message
                            val errorMessage = result.errorMessage
                            Log.e("errorLoginError", "$errorMessage")
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        }
    }

}