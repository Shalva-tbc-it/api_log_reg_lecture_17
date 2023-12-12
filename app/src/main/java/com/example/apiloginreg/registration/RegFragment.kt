package com.example.apiloginreg.registration

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.apiloginreg.R
import com.example.apiloginreg.api.RetrofitInstance
import com.example.apiloginreg.auth.AuthRepository
import com.example.apiloginreg.auth.AuthResult
import com.example.apiloginreg.auth.AuthViewModel
import com.example.apiloginreg.auth.AuthViewModelFactory
import com.example.apiloginreg.base.BaseFragment
import com.example.apiloginreg.databinding.FragmentRegBinding
import kotlinx.coroutines.launch

class RegFragment : BaseFragment<FragmentRegBinding>(FragmentRegBinding::inflate) {

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(AuthRepository(RetrofitInstance.apiService))
    }

    override fun start() {
        observe()
    }

    override fun clickListener() {

        binding.apply {

            btnReg.setOnClickListener {
                authViewModel.registerUser(edEmail.text.toString(), edPass.text.toString())
            }

            tvGoToLog.setOnClickListener {
                R.id.action_regFragment_to_loginFragment
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
                                // Success registration
                                val token = result.token
                                Toast.makeText(requireContext(), "$token", Toast.LENGTH_SHORT).show()
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