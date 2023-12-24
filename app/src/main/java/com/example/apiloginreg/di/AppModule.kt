package com.example.apiloginreg.di

import com.example.apiloginreg.data.log_in.LogInService
import com.example.apiloginreg.data.registration.RegistrationService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://reqres.in/api/"

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
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()))
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


}