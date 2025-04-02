package com.joel.timiza.data.datastore

import kotlinx.coroutines.flow.Flow

interface SessionService {
    suspend fun saveOnAuthStatus(completed : Boolean)
    fun readAuthStatus() : Flow<Boolean>
}