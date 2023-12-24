package com.example.apiloginreg.presentation.registration

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.apiloginreg.R
import com.example.apiloginreg.auth.AuthResult
import com.example.apiloginreg.databinding.FragmentRegBinding
import com.example.apiloginreg.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RegFragment : BaseFragment<FragmentRegBinding>(FragmentRegBinding::inflate) {

    private val regViewModel: RegistrationViewModel by viewModels()

    private var currentPass: Boolean = false

    override fun start() {
        observe()
        checkPass()
    }

    override fun clickListener() {

        binding.apply {

            btnReg.setOnClickListener {
                if (currentPass) {
                    regViewModel.registerUser(edEmail.text.toString(), edPass.text.toString())
                } else {
                    Toast.makeText(requireContext(), "check pass", Toast.LENGTH_SHORT).show()
                }
            }

            tvGoToLog.setOnClickListener {
                findNavController().navigate(
                    RegFragmentDirections.actionRegFragmentToLoginFragment()
                )
            }
        }

    }

    private fun checkPass() = with(binding) {
        edPass.doAfterTextChanged {
            checkPasswordsMatch()
        }
        edRepeatPass.doAfterTextChanged {
            checkPasswordsMatch()
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                regViewModel.registerResult.collect { result ->
                    result?.let {
                        when (result) {
                            is AuthResult.Success -> {
                                // Success registration
                                val token = result.token
                                findNavController().navigate(
                                    RegFragmentDirections.actionRegFragmentToLoginFragment()
                                )
                                Toast.makeText(requireContext(), "$token", Toast.LENGTH_SHORT)
                                    .show()
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

    private fun checkPasswordsMatch() = with(binding) {
        if (edPass.text.toString() == edRepeatPass.text.toString()) {
            edPass.setTextColor(ContextCompat.getColor(requireContext(), R.color.passwordMatch))
            edRepeatPass.setTextColor(
                ContextCompat.getColor(
                    requireContext(), R.color.passwordMatch
                )
            )
            currentPass = true
        } else {
            edPass.setTextColor(ContextCompat.getColor(requireContext(), R.color.passwordMismatch))
            edRepeatPass.setTextColor(
                ContextCompat.getColor(
                    requireContext(), R.color.passwordMismatch
                )
            )
            currentPass = false
        }
    }
}