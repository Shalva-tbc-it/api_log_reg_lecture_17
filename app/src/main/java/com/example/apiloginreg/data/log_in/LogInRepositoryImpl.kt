package com.example.apiloginreg.data.log_in

import com.example.apiloginreg.data_store.AuthResult
import com.example.apiloginreg.domain.LogInRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(val logInService: LogInService) : LogInRepository {

    override suspend fun loginUser(email: String, password: String): Flow<AuthResult> = flow {
        try {
            val response = logInService.loginUser(LogInDTO(email, password))
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

}