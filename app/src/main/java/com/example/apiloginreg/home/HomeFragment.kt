package com.example.apiloginreg.home

import androidx.navigation.fragment.findNavController
import com.example.apiloginreg.R
import com.example.apiloginreg.base.BaseFragment
import com.example.apiloginreg.databinding.FragmentHomeBinding
import com.example.apiloginreg.login.TokenManager

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {


    override fun start() {

    }

    override fun clickListener() {
        binding.apply {
            btnLogOut.setOnClickListener {
                TokenManager(requireContext()).clearToken()
                findNavController().navigate(
                    R.id.action_homeFragment_to_loginFragment
                )
            }
        }
    }


}