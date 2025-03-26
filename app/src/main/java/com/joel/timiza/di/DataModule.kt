package com.joel.timiza.di

import android.content.Context
import com.joel.timiza.data.auth.AuthService
import com.joel.timiza.data.auth.AuthServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract  class DataModule {

    @Binds
    abstract fun bindsAuthService(service: AuthServiceImpl): AuthService

}