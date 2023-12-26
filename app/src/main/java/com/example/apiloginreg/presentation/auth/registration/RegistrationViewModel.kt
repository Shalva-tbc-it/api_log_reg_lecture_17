package com.example.apiloginreg.presentation.auth.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiloginreg.data.registration.RegistrationDTO
import com.example.apiloginreg.domain.RegistrationRepository
import com.example.apiloginreg.presentation.auth.AuthResult
import com.example.apiloginreg.presentation.auth.log_in.LogInViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: RegistrationRepository
) : ViewModel() {

    sealed class RegEvent {
        data object LoginSuccess : RegEvent()
        data object LoginFailure : RegEvent()
        data object Loading : RegEvent()
        data class Registration(val user: RegistrationDTO) : RegEvent()
    }

    private val _event = MutableStateFlow<LogInViewModel.LoginEvent>(LogInViewModel.LoginEvent.Loading)
    val event: StateFlow<LogInViewModel.LoginEvent> get() = _event


    fun regEvent(event: RegEvent.Registration) {
        viewModelScope.launch {
            try {
                when(event){
                    is RegEvent.Registration -> {
                        registerUser(event.user.email, event.user.password)
                    }
                    else -> {

                    }
                }

            } catch (e: Throwable) {
                _event.value = LogInViewModel.LoginEvent.LoginFailure
            }
        }
    }
    private val _registerResult = MutableStateFlow<AuthResult?>(null)
    val registerResult: StateFlow<AuthResult?> get() = _registerResult

    private fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            repository.registerUser(email, password).collect {
                _registerResult.value = it
            }
        }
    }
}