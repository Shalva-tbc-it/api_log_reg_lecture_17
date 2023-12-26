package com.example.apiloginreg.presentation.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiloginreg.data.data_store_pref.UserDataStoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val userDataStoreService: UserDataStoreService
) : ViewModel() {



    fun saveToken(token: String) {
        viewModelScope.launch {
            userDataStoreService.saveToken(token)
        }
    }

    fun clearToken() {
        viewModelScope.launch {
            userDataStoreService.clearToken()
        }
    }

    fun observeToken(): Flow<String?> {
        return userDataStoreService.getToken()
    }

}
