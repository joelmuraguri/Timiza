package com.joel.timiza.presentation.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.timiza.data.datastore.SessionService
import com.joel.timiza.presentation.navigation.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val service: SessionService
) : ViewModel() {

    private val _startDestination: MutableState<Destinations> = mutableStateOf(Destinations.SignIn)
    val startDestination: State<Destinations> = _startDestination

    init {
        viewModelScope.launch {
            service.readAuthStatus().collect { completed ->
                if (completed) {
                    _startDestination.value = Destinations.TodoList
                } else {
                    _startDestination.value = Destinations.SignIn
                }
            }
        }
    }

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            service.saveOnAuthStatus(completed = completed)
        }
    }
}