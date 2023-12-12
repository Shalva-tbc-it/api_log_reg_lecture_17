package com.example.apiloginreg.auth

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val email: String, val password: String)
data class ApiResponse(val token: String)