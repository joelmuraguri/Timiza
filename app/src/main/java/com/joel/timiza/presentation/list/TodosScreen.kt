package com.joel.timiza.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.joel.timiza.domain.models.User
import com.joel.timiza.utils.TimizaEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodosScreen(
    user: User,
    onNavigate : (TimizaEvents.Navigate) -> Unit,
    popBackStack : () -> Unit,
    todoViewModel: TodoViewModel = viewModel()
){

    LaunchedEffect(key1 = true){
        todoViewModel.uiEvents.collect{ timizaEvents ->
            when(timizaEvents){
                is TimizaEvents.Navigate -> {
                    onNavigate(timizaEvents)
                }
                TimizaEvents.PopBackStack -> {
                    popBackStack()
                }
                is TimizaEvents.ShowSnackbar -> TODO()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text( "Hi ${user.name}")
                },
                navigationIcon = {

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    todoViewModel.onEvents(TodoScreenEvent.OnAddTodo)
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                ) {
                    Icon(Icons.Default.Add, null)
                    Text("Add Todo")
                }
            }
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            Text("TODOS")
        }
    }
}