package com.joel.timiza.presentation.design.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmailTextField(
    email : String,
    onEmailChange : (String) -> Unit
){

    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        modifier = Modifier
            .fillMaxSize(),
        placeholder = {
            Text("Enter Email Address")
        },
    )
}

@Composable
fun NameTextField(
    name : String,
    onNameChange : (String) -> Unit
){

    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        modifier = Modifier
            .fillMaxSize(),
        placeholder = {
            Text("Enter Name")
        },
    )
}

@Composable
fun PasswordTextField(
    password : String,
    onPasswordChange : (String) -> Unit
){

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier
            .fillMaxSize(),
        placeholder = {
            Text("Enter Password")
        },
    )
}

@Composable
fun TitleField(
    title : String,
    onTitleChange : (String) -> Unit
){

    OutlinedTextField(
        value = title,
        onValueChange = onTitleChange,
        modifier = Modifier
            .fillMaxSize(),
        placeholder = {
            Text("Enter Title..")
        },
    )
}

@Composable
fun ContentTextField(
    content : String,
    onContentChange : (String) -> Unit
){

    OutlinedTextField(
        value = content,
        onValueChange = onContentChange,
        modifier = Modifier
            .height(150.dp)
            .fillMaxSize(),
        placeholder = {
            Text("Enter Email Address")
        },
        maxLines = 5
    )
}