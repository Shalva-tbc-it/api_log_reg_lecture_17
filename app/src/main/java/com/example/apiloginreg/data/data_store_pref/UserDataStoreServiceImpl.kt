package com.example.apiloginreg.data.data_store_pref

import com.example.apiloginreg.data_store.TokenManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataStoreServiceImpl @Inject constructor(
    private val tokenManager: TokenManager
) : UserDataStoreService {

    override suspend fun saveToken(token: String) {
        tokenManager.saveToken(token)
    }

    override suspend fun clearToken() {
        tokenManager.clearToken()
    }

    override fun getToken(): Flow<String?> {
        return tokenManager.getToken
    }
}
