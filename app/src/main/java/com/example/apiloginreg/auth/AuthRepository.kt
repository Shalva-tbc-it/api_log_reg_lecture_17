package com.example.apiloginreg.auth

import com.example.apiloginreg.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject

class AuthRepository(private val apiService: ApiService) {

    suspend fun loginUser(email: String, password: String): Flow<AuthResult> = flow {
        try {
            val response = apiService.loginUser(LoginRequest(email, password))
            if (response.isSuccessful) {
                val token = response.body()?.token
                if (token != null) {
                    emit(AuthResult.Success(token))
                } else {
                    emit(AuthResult.Error("Login failed: Token is null"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                if (response.code() == 400 && !errorBody.isNullOrBlank()) {
                    // 400 error
                    val errorMessage = try {
                        val json = JSONObject(errorBody)
                        json.optString("error", "Unknown error")
                    } catch (e: JSONException) {
                        "Unknown error"
                    }
                    emit(AuthResult.Error("Login failed: $errorMessage"))
                } else {
                    // others error
                    emit(AuthResult.Error("Login failed: ${response.code()}"))
                }
            }
        } catch (e: Throwable) {
            emit(AuthResult.Error("Login failed: ${e.message}"))
        }
    }

    suspend fun registerUser(email: String, password: String): Flow<AuthResult> = flow {
        try {
            val response = apiService.registerUser(RegisterRequest(email, password))
            if (response.isSuccessful) {
                val token = response.body()?.token
                if (token != null) {
                    emit(AuthResult.Success(token))

                } else {
                    emit(AuthResult.Error("Registration failed: Token is null"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                if (response.code() == 400 && !errorBody.isNullOrBlank()) {
                    // 400 error
                    val errorMessage = try {
                        val json = JSONObject(errorBody)
                        json.optString("error", "Unknown error")
                    } catch (e: JSONException) {
                        "Unknown error"
                    }
                    emit(AuthResult.Error("Registration failed: $errorMessage"))
                } else {
                    // others error
                    emit(AuthResult.Error("Registration failed: ${response.code()}"))
                }
            }
        } catch (e: Throwable) {
            emit(AuthResult.Error("Registration failed: ${e.message}"))
        }
    }

}