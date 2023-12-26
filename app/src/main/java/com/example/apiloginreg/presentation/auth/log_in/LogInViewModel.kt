package com.example.apiloginreg.presentation.auth.log_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiloginreg.data.log_in.LogInDTO
import com.example.apiloginreg.domain.LogInRepository
import com.example.apiloginreg.presentation.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val repository: LogInRepository
) : ViewModel() {

    sealed class LoginEvent {
        data object LoginSuccess : LoginEvent()
        data object LoginFailure : LoginEvent()
        data object Loading : LoginEvent()
        data class Login(val user: LogInDTO) : LoginEvent()
    }

    private val _event = MutableStateFlow<LoginEvent>(LoginEvent.Loading)
    val event: StateFlow<LoginEvent> get() = _event


    fun loginEvent(event: LoginEvent) {
        viewModelScope.launch {
            try {
                when(event){
                    is LoginEvent.Login -> {
                       loginUser(event.user)
                        _event.value = LoginEvent.LoginSuccess
                    }
                    else -> {
                        _event.value = LoginEvent.LoginFailure
                    }
                }

            } catch (e: Throwable) {
                _event.value = LoginEvent.LoginFailure
            }
        }
    }

    private val _loginResult = MutableStateFlow<AuthResult?>(null)
    val loginResult: StateFlow<AuthResult?> get() = _loginResult

    private fun loginUser(user: LogInDTO) {
        viewModelScope.launch {
            repository.loginUser(user.email, user.password).collect {
                _loginResult.value = it
            }
        }
    }
}