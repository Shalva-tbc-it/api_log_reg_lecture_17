package com.example.apiloginreg.login

import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.apiloginreg.api.RetrofitInstance
import com.example.apiloginreg.auth.AuthRepository
import com.example.apiloginreg.auth.AuthResult
import com.example.apiloginreg.auth.AuthViewModel
import com.example.apiloginreg.auth.AuthViewModelFactory
import com.example.apiloginreg.base.BaseFragment
import com.example.apiloginreg.databinding.FragmentLoginBinding
import com.example.apiloginreg.token.TokenManager
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(AuthRepository(RetrofitInstance.apiService))
    }

    override fun start() {
        observe()
    }

    override fun clickListener() {
        binding.apply {

            btnLogin.setOnClickListener {
                if (isValidEmail(edEmail.text.toString())) {
                    authViewModel.loginUser(edEmail.text.toString(), edPass.text.toString())
                } else {
                    Toast.makeText(requireContext(), "wrong email", Toast.LENGTH_SHORT).show()
                }
            }

            tvGoToReg.setOnClickListener {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToRegFragment()
                )
            }
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    TokenManager(requireContext()).getToken.collect {
                        if (!it.isNullOrEmpty()) {
                            findNavController().navigate(
                                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                            )
                        }
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    //email
                }
            }
        }
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.loginResult.collect { result ->
                    result?.let {
                        when (result) {
                            is AuthResult.Success -> {
                                // Success login
                                val token = result.token
                                if (binding.checkbox.isChecked) {
                                    viewLifecycleOwner.lifecycleScope.launch {
                                        TokenManager(requireContext()).saveToken(token)
                                    }
                                }
                                findNavController().navigate(
                                    LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                                )
                            }

                            is AuthResult.Error -> {
                                // Error message
                                val errorMessage = result.errorMessage
                                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }

}