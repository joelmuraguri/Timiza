package com.joel.timiza.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.timiza.presentation.navigation.Destinations
import com.joel.timiza.utils.TimizaEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AuthViewModel() : ViewModel() {

    private val _uiEvents = Channel<TimizaEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun onEvents(events: AuthEvents){
        when(events){
            AuthEvents.OnNavHome -> {
                viewModelScope.launch {
                    Log.d(AUTH_VIEWMODEL_TAG, "-----------> on nav home click")
                    _uiEvents.send(TimizaEvents.Navigate(Destinations.TodoList))
                }
            }
            AuthEvents.OnNavSignIn -> {
                viewModelScope.launch {
                    Log.d(AUTH_VIEWMODEL_TAG, "-----------> on nav sign in click")
                    _uiEvents.send(TimizaEvents.Navigate(Destinations.SignIn))
                }
            }
            AuthEvents.OnNavSignUp -> {
                viewModelScope.launch {
                    Log.d(AUTH_VIEWMODEL_TAG, "-----------> on nav sign up click")
                    _uiEvents.send(TimizaEvents.Navigate(Destinations.SignUp))
                }
            }
        }
    }

    companion object{
        const val AUTH_VIEWMODEL_TAG = "AuthViewModel"
    }
}

sealed class AuthEvents{
    data object OnNavHome : AuthEvents()
    data object OnNavSignIn : AuthEvents()
    data object OnNavSignUp : AuthEvents()
}

