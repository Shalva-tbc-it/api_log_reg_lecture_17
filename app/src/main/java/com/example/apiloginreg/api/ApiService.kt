package com.example.apiloginreg.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): Response<ApiResponse>
    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<ApiResponse>
}