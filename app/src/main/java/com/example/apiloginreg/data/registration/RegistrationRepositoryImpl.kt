package com.example.apiloginreg.data.registration

import com.example.apiloginreg.auth.AuthResult
import com.example.apiloginreg.auth.RegisterRequest
import com.example.apiloginreg.domain.RegistrationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(private val registrationService: RegistrationService): RegistrationRepository {

    override suspend fun registerUser(email: String, password: String): Flow<AuthResult> {
       return flow {
           try {
               val response = registrationService.registerUser(RegisterRequest(email, password))
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

}