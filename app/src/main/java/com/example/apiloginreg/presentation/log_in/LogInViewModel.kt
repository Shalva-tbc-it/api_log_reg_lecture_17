package com.example.apiloginreg.presentation.log_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiloginreg.data_store.AuthResult
import com.example.apiloginreg.domain.LogInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val repository: LogInRepository
) : ViewModel() {

    sealed class LoginEvent {

    }

    private val event = Channel<LoginEvent>()



    private val _loginResult = MutableStateFlow<AuthResult?>(null)
    val loginResult: StateFlow<AuthResult?> get() = _loginResult

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            repository.loginUser(email, password).collect {
                _loginResult.value = it
            }
        }
    }
}