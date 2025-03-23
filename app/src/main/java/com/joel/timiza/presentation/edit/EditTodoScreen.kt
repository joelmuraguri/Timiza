package com.joel.timiza.presentation.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.joel.timiza.domain.models.Todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTodosScreen(
    todo : Todo
){

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(todo.title)
                },
                navigationIcon = {

                }
            )
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