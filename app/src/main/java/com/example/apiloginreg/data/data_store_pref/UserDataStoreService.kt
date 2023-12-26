package com.example.apiloginreg.data.data_store_pref

import kotlinx.coroutines.flow.Flow

interface UserDataStoreService {
    suspend fun saveToken(token: String)
    fun getToken(): Flow<String?>
    suspend fun clearToken()

}
