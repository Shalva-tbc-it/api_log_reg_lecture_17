package com.example.apiloginreg.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.apiloginreg.data.data_store_pref.UserDataStoreService
import com.example.apiloginreg.data.data_store_pref.UserDataStoreServiceImpl
import com.example.apiloginreg.data.log_in.LogInService
import com.example.apiloginreg.data.registration.RegistrationService
import com.example.apiloginreg.data_store.TokenManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


private const val BASE_URL = "https://reqres.in/api/"
private const val PREFERENCES = "preferences"


@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Provides
    @Singleton
    fun client() = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .build()
            chain.proceed(request)
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Provides
    @Singleton
    fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .baseUrl(BASE_URL)
            .client(client())
            .build()
    }

    @Provides
    @Singleton
    fun provideLogInService(retrofit: Retrofit): LogInService {
        return retrofit.create(LogInService::class.java)
    }

    @Provides
    @Singleton
    fun provideRegistrationService(retrofit: Retrofit): RegistrationService {
        return retrofit.create(RegistrationService::class.java)
    }

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext appContext: Context) : DataStore<Preferences> {
        return PreferenceDataStoreFactory.create (
            corruptionHandler = ReplaceFileCorruptionHandler (
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = { appContext.preferencesDataStoreFile(PREFERENCES) }
        )
    }

    @Provides
    @Singleton
    fun provideUserDataStoreService(tokenManager: TokenManager): UserDataStoreService {
        return UserDataStoreServiceImpl(tokenManager)
    }


}