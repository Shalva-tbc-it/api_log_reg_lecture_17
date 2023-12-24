package com.example.apiloginreg.domain

import com.example.apiloginreg.data_store.AuthResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.POST

interface RegistrationRepository {
    @POST("register")
    suspend fun registerUser(email: String, password: String): Flow<AuthResult>
}