package com.example.apiloginreg.registration

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.apiloginreg.api.AuthRepository
import com.example.apiloginreg.api.AuthResult
import com.example.apiloginreg.api.AuthViewModel
import com.example.apiloginreg.api.AuthViewModelFactory
import com.example.apiloginreg.api.RetrofitInstance
import com.example.apiloginreg.base.BaseFragment
import com.example.apiloginreg.databinding.FragmentRegBinding
import kotlinx.coroutines.launch

class RegFragment : BaseFragment<FragmentRegBinding>(FragmentRegBinding::inflate) {

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(AuthRepository(RetrofitInstance.apiService))
    }

    override fun start() {

    }

    override fun clickListener() {

        binding.apply {

            btnLogin.setOnClickListener {
                authViewModel.registerUser(edEmail.text.toString(), edPass.text.toString())
                observe()
            }

        }

    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
            authViewModel.registerResult.collect { result ->
                result?.let {
                    when (result) {
                        is AuthResult.Success -> {
                            // Success login
                            val token = result.token
                            Toast.makeText(requireContext(), token, Toast.LENGTH_SHORT).show()
//                            findNavController().navigate(
//                                R.id.action_loginFragment_to_homeFragment
//                            )
                        }
                        is AuthResult.Error -> {
                            // Error message
                            val errorMessage = result.errorMessage
                            Log.e("errorLoginError", "$errorMessage")
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