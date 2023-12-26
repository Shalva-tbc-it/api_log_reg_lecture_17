package com.example.apiloginreg.data.log_in

import com.example.apiloginreg.presentation.auth.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInService {
    @POST("login")
    suspend fun loginUser(@Body request: LogInDTO): Response<ApiResponse>
}