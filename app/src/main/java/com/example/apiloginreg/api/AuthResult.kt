package com.example.apiloginreg.api

sealed class AuthResult {
    data class Success(val token: String) : AuthResult()
    data class Error(val errorMessage: String) : AuthResult()

}


//sealed class AuthResult<out T>(
//    val data: T? = null,
//    val errorMessage: String? = null,
//    val loading: Boolean = false
//) {
//    class Success<out T>(data: T) : AuthResult<T>(data = data)
//    class Error<out T>(errorMessage: String) : AuthResult<T>(errorMessage = errorMessage)
//    class Loading<out T>(loading: Boolean) : AuthResult<T>(loading = loading)
//}