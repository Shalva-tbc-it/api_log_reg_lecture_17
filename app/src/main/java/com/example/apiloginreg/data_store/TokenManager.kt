package com.example.apiloginreg.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token_key")
    }

    suspend fun saveToken(token: String) {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[TOKEN_KEY] = token
            }
        }
    }
    suspend fun clearToken() {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences.remove(TOKEN_KEY)
            }
        }

    }

    val getToken: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }

}

