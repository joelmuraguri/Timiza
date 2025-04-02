package com.joel.timiza.di

import com.joel.timiza.data.auth.AuthService
import com.joel.timiza.data.auth.AuthServiceImpl
import com.joel.timiza.data.database.StorageService
import com.joel.timiza.data.database.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract  class DataModule {

    @Binds
    abstract fun bindsAuthService(service: AuthServiceImpl): AuthService

    @Binds
    abstract fun bindsStorageService(service: StorageServiceImpl): StorageService

}