package com.example.apiloginreg.auth

sealed class AuthResult {
    data class Success(val token: String) : AuthResult()
    data class Error(val errorMessage: String) : AuthResult()

}
