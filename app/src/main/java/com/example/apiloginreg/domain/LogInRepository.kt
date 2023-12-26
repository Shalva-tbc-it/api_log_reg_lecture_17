package com.example.apiloginreg.domain

import com.example.apiloginreg.presentation.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface LogInRepository {
    suspend fun loginUser(email: String, password: String): Flow<AuthResult>
}