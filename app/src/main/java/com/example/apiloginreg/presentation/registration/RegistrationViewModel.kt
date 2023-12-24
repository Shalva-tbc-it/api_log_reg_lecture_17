package com.example.apiloginreg.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiloginreg.data_store.AuthResult
import com.example.apiloginreg.domain.RegistrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: RegistrationRepository
) : ViewModel() {

    private val _registerResult = MutableStateFlow<AuthResult?>(null)
    val registerResult: StateFlow<AuthResult?> get() = _registerResult

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            repository.registerUser(email, password).collect {
                _registerResult.value = it
            }
        }
    }
}