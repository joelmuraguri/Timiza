package com.joel.timiza.di

import android.content.Context
import com.joel.timiza.data.datastore.SessionService
import com.joel.timiza.data.datastore.SessionServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {
    @Provides
    @Singleton
    fun provideSessionService(@ApplicationContext context: Context): SessionService {
        return SessionServiceImpl(context)
    }
}
