package com.example.apiloginreg.di

import com.example.apiloginreg.data.log_in.LogInRepositoryImpl
import com.example.apiloginreg.data.log_in.LogInService
import com.example.apiloginreg.domain.LogInRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLogInRepository (logInService: LogInService) : LogInRepository {
        return LogInRepositoryImpl(logInService = logInService)
    }

}