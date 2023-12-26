package com.example.apiloginreg.data.registration

import com.example.apiloginreg.presentation.auth.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationService {

    @POST("register")
    suspend fun registerUser(@Body request: RegistrationDTO): Response<ApiResponse>
}