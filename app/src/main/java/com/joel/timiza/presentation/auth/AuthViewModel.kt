package com.joel.timiza.presentation.auth

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.timiza.data.auth.AuthResponse
import com.joel.timiza.data.auth.AuthService
import com.joel.timiza.presentation.navigation.Destinations
import com.joel.timiza.utils.TimizaEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val service : AuthService
) : ViewModel() {

    private val _state = mutableStateOf(AuthScreenState())
    val state : State<AuthScreenState> = _state

    private val _uiEvents = Channel<TimizaEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private val _isUserAuthenticated = MutableStateFlow(service.hasUser)
    val isUserAuthenticated: StateFlow<Boolean> = _isUserAuthenticated

    private fun checkAuthStatus() {
        _isUserAuthenticated.value = service.hasUser
    }

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
            is AuthEvents.OnGoogleSignIn -> {
                viewModelScope.launch {
                    service.googleSignIn(events.activity).collect{
                        when(it){
                            is AuthResponse.Error -> {
                                Log.e(AUTH_VIEWMODEL_TAG, "Google Sign In Failed: ${it.message}")
                                _uiEvents.send(TimizaEvents.ShowSnackbar("Google Sign In Failed: ${it.message}"))
                            }
                            AuthResponse.Success -> {
                                checkAuthStatus()
                                _uiEvents.send(TimizaEvents.ShowSnackbar("Sign In Successful!"))
                                delay(2000)
                                _uiEvents.send(TimizaEvents.Navigate(Destinations.TodoList))
                            }
                        }
                    }
                }
            }
            AuthEvents.OnSignIn -> {
                viewModelScope.launch {
                    service.signIn(
                        emailValue = _state.value.email,
                        passwordValue = _state.value.password
                    ).collect{
                        when(it){
                            is AuthResponse.Error -> {
                                Log.e(AUTH_VIEWMODEL_TAG, "Sign-In Failed: ${it.message}")
                                _uiEvents.send(TimizaEvents.ShowSnackbar("Sign-In Failed: ${it.message}"))
                            }
                            AuthResponse.Success -> {
                                checkAuthStatus()
                                _uiEvents.send(TimizaEvents.ShowSnackbar("Sign In Successful!"))
                                delay(2000)
                                _uiEvents.send(TimizaEvents.Navigate(Destinations.TodoList))
                            }
                        }
                    }
                }
            }
            AuthEvents.OnSignUp -> {
                viewModelScope.launch {
                    service.register(
                        emailValue = _state.value.email,
                        passwordValue = _state.value.password,
                        name = _state.value.name
                    ).collect{
                        when(it){
                            is AuthResponse.Error -> {
                                Log.e(AUTH_VIEWMODEL_TAG, "Sign-Up Failed: ${it.message}")
                                _uiEvents.send(TimizaEvents.ShowSnackbar("Sign-Up Failed: ${it.message}"))
                            }
                            AuthResponse.Success -> {
                                checkAuthStatus()
                                _uiEvents.send(TimizaEvents.ShowSnackbar("Sign Up Successful!"))
                                delay(2000)
                                _uiEvents.send(TimizaEvents.Navigate(Destinations.TodoList))
                            }
                        }
                    }
                }
            }

            is AuthEvents.OnConfirmPasswordChange -> {
                _state.value = _state.value.copy(confirmPassword = events.confirmPassword)

            }
            is AuthEvents.OnEmailChange -> {
                _state.value = _state.value.copy(email = events.email)

            }
            is AuthEvents.OnNameChange -> {
                _state.value = _state.value.copy(name = events.name)
            }
            is AuthEvents.OnPasswordChange -> {
                _state.value = _state.value.copy(password = events.password)

            }
        }
    }

    companion object{
        const val AUTH_VIEWMODEL_TAG = "AuthViewModel"
    }
}

data class AuthScreenState(
    var name : String = "",
    var email : String = "",
    var password : String = "",
    var confirmPassword : String = "",
)

sealed class AuthEvents{
    data object OnNavHome : AuthEvents()
    data object OnNavSignIn : AuthEvents()
    data object OnNavSignUp : AuthEvents()
    data object OnSignIn : AuthEvents()
    data class OnGoogleSignIn(val activity: Activity) : AuthEvents()
    data object OnSignUp : AuthEvents()
    data class OnNameChange(val name : String) : AuthEvents()
    data class OnEmailChange(val email : String) : AuthEvents()
    data class OnPasswordChange(val password: String) : AuthEvents()
    data class OnConfirmPasswordChange(val confirmPassword: String ) : AuthEvents()
}

