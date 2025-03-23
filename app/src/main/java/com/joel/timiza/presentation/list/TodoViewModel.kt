package com.joel.timiza.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.timiza.presentation.navigation.Destinations
import com.joel.timiza.utils.TimizaEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {

    private val _uiEvents = Channel<TimizaEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun onEvents(events: TodoScreenEvent){
        when(events){
            TodoScreenEvent.OnAddTodo -> {
                viewModelScope.launch {
                    _uiEvents.send(TimizaEvents.Navigate(Destinations.TodoEdit))
                }
            }
        }
    }
}


sealed class TodoScreenEvent{
    data object OnAddTodo : TodoScreenEvent()
}