package com.example.apiloginreg.di

import com.example.apiloginreg.data.log_in.LogInRepositoryImpl
import com.example.apiloginreg.data.log_in.LogInService
import com.example.apiloginreg.data.registration.RegistrationRepositoryImpl
import com.example.apiloginreg.data.registration.RegistrationService
import com.example.apiloginreg.domain.LogInRepository
import com.example.apiloginreg.domain.RegistrationRepository
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

    @Singleton
    @Provides
    fun provideRegistrationRepository (registrationService: RegistrationService) : RegistrationRepository {
        return RegistrationRepositoryImpl(registrationService = registrationService)
    }

}