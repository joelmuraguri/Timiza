package com.joel.timiza.data.auth

import android.app.Activity
import com.joel.timiza.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>
    suspend fun register(emailValue: String, passwordValue: String, name : String) : Flow<AuthResponse>
    suspend fun googleSignIn(activity: Activity) : Flow<AuthResponse>
    suspend fun signOut() : Flow<AuthResponse>
    suspend fun signIn(emailValue: String, passwordValue: String) : Flow<AuthResponse>
}