package com.example.apiloginreg.presentation.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.apiloginreg.databinding.FragmentHomeBinding
import com.example.apiloginreg.presentation.base.BaseFragment
import com.example.apiloginreg.presentation.splash_screen.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: TokenViewModel by viewModels()

    override fun start() {

    }

    override fun clickListener() {
        binding.apply {
            btnLogOut.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.clearToken()
                    }
                }
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                )
            }
        }
    }


}