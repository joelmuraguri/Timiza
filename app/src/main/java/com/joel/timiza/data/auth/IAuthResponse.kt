package com.joel.timiza.data.auth

sealed interface AuthResponse {
    data class Error(val message : String?) : AuthResponse
    data object Success : AuthResponse
}
