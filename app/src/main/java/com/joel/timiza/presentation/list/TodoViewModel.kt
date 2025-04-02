package com.joel.timiza.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.timiza.data.datastore.SessionService
import com.joel.timiza.domain.models.Category
import com.joel.timiza.domain.models.Todo
import com.joel.timiza.domain.models.User
import com.joel.timiza.presentation.navigation.Destinations
import com.joel.timiza.utils.RequestState
import com.joel.timiza.utils.TimizaEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val sessionService: SessionService
): ViewModel() {

    private val _uiEvents = Channel<TimizaEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> = _user

    private val _allTodos = MutableStateFlow<RequestState<List<Todo>>>(RequestState.Idle)
    val allTodos : StateFlow<RequestState<List<Todo>>> = _allTodos

    private val _allCategories = MutableStateFlow<RequestState<List<Category>>>(RequestState.Idle)
    val allCategories : StateFlow<RequestState<List<Category>>> = _allCategories

    private val _state = mutableStateOf(TodoScreenState())
    val state : State<TodoScreenState> = _state

    init {
        viewModelScope.launch {
            sessionService.readUserData().collect { userData ->
                _user.value = userData
            }
        }
    }

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

data class TodoScreenState(
    val categoryTitle: String = "",
    val showDialog : Boolean = false
)