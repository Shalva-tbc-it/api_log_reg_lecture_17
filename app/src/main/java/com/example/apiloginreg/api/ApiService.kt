package com.example.apiloginreg.api

import com.example.apiloginreg.auth.ApiResponse
import com.example.apiloginreg.auth.LoginRequest
import com.example.apiloginreg.auth.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): Response<ApiResponse>
    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<ApiResponse>
}