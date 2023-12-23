package com.example.apiloginreg.data.log_in

import com.example.apiloginreg.auth.ApiResponse
import com.example.apiloginreg.auth.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInService {

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): Response<ApiResponse>
}