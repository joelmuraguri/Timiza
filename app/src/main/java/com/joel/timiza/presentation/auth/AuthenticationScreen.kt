package com.joel.timiza.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp


//@Composable
//fun AuthScreen(viewModel: AuthViewModel) {
//    val context = LocalContext.current
//
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(text = if (viewModel.isLogin) "Login" else "Register", style = MaterialTheme.typography.headlineMedium)
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = viewModel.email,
//            onValueChange = { viewModel.email = it },
//            label = { Text("Email") }
//        )
//
//        if (!viewModel.isLogin) {
//            OutlinedTextField(
//                value = viewModel.fullName,
//                onValueChange = { viewModel.fullName = it },
//                label = { Text("Full Name") }
//            )
//        }
//
//        OutlinedTextField(
//            value = viewModel.password,
//            onValueChange = { viewModel.password = it },
//            label = { Text("Password") },
//            visualTransformation = PasswordVisualTransformation()
//        )
//
//        if (!viewModel.isLogin) {
//            OutlinedTextField(
//                value = viewModel.confirmPassword,
//                onValueChange = { viewModel.confirmPassword = it },
//                label = { Text("Confirm Password") },
//                visualTransformation = PasswordVisualTransformation()
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(onClick = { viewModel.authenticate(context) }) {
//            Text(text = if (viewModel.isLogin) "Login" else "Register")
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        TextButton(onClick = { viewModel.isLogin = !viewModel.isLogin }) {
//            Text(text = if (viewModel.isLogin) "Don't have an account? Register" else "Already have an account? Login")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(onClick = { viewModel.signInWithGoogle(context) }) {
//            Text(text = "Sign in with Google")
//        }
//    }
//}

