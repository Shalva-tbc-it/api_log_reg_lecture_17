package com.example.apiloginreg.data_store

sealed class AuthResult {
    data class Success(val token: String) : AuthResult()
    data class Error(val errorMessage: String) : AuthResult()

}
