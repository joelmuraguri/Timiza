package com.joel.timiza.data.datastore

import com.joel.timiza.domain.models.User
import kotlinx.coroutines.flow.Flow

interface SessionService {
    suspend fun saveOnAuthStatus(completed : Boolean)
    fun readAuthStatus() : Flow<Boolean>

    suspend fun saveUserData(user: User)
    fun readUserData() : Flow<User>
    suspend fun clearUserData()
}