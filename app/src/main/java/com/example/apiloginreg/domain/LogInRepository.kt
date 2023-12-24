package com.example.apiloginreg.domain

import com.example.apiloginreg.data_store.AuthResult
import kotlinx.coroutines.flow.Flow

interface LogInRepository {
    suspend fun loginUser(email: String, password: String): Flow<AuthResult>
}