package com.example.apiloginreg.data.registration

import com.example.apiloginreg.auth.ApiResponse
import com.example.apiloginreg.auth.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationService {

    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<ApiResponse>
}