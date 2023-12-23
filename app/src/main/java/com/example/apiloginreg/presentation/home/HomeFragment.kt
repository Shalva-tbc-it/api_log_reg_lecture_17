package com.example.apiloginreg.presentation.home

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.apiloginreg.presentation.BaseFragment
import com.example.apiloginreg.databinding.FragmentHomeBinding
import com.example.apiloginreg.token.TokenManager
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun start() {

    }

    override fun clickListener() {
        binding.apply {
            btnLogOut.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        TokenManager(requireContext()).clearToken()
                    }
                }
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                )
            }
        }
    }


}