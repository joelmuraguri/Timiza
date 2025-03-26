package com.joel.timiza.presentation.auth

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.joel.timiza.R
import com.joel.timiza.ui.theme.TimizaTheme
import com.joel.timiza.ui.theme.black
import com.joel.timiza.ui.theme.darkGray
import com.joel.timiza.utils.Gradient
import com.joel.timiza.utils.TimizaEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onNavigate : (TimizaEvents.Navigate) -> Unit,
    popBackStack : () -> Unit,
    scope : CoroutineScope,
    snackbarHostState : SnackbarHostState,
    activity: Activity
) {


    LaunchedEffect(key1 = true){
        authViewModel.uiEvents.collect{ timizaEvents ->
            when(timizaEvents){
                is TimizaEvents.Navigate -> {
                    onNavigate(timizaEvents)
                }
                TimizaEvents.PopBackStack -> {
                    popBackStack()
                }
                is TimizaEvents.ShowSnackbar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(timizaEvents.message)
                    }
                }
            }
        }
    }

    val state = authViewModel.state.value

    val scrollState = rememberScrollState()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(black),
            contentAlignment = Alignment.TopCenter
        ) {
            Gradient()

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .padding(top = 110.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RegisterHeader()

                Spacer(modifier = Modifier.height(40.dp))

                GoogleSignInButton(
                    onClick = {
                        authViewModel.onEvents(AuthEvents.OnGoogleSignIn(activity))
                    }
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 30.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Color.White.copy(alpha = 0.2f))
                    )

                    Text(
                        text = "Or",
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Color.White.copy(alpha = 0.2f))
                    )
                }

                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Name",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    TextField(
                        value = state.name,
                        onValueChange = { newValue ->
                            authViewModel.onEvents(AuthEvents.OnNameChange(newValue))
                        },
                        placeholder = {
                            Text(
                                text = "John Doe",
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = darkGray,
                            unfocusedContainerColor = darkGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Email",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    TextField(
                        value = state.email,
                        onValueChange = { newValue ->
                            authViewModel.onEvents(AuthEvents.OnEmailChange(newValue))
                        },
                        placeholder = {
                            Text(
                                text = "john.doe@example.com",
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = darkGray,
                            unfocusedContainerColor = darkGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Password",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    TextField(
                        value = state.password,
                        onValueChange = { newValue ->
                            authViewModel.onEvents(AuthEvents.OnPasswordChange(newValue))
                        },
                        placeholder = {
                            Text(
                                text = "Enter your password",
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = darkGray,
                            unfocusedContainerColor = darkGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(35.dp))

                Button(
                    onClick = {
                        authViewModel.onEvents(AuthEvents.OnSignUp)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Sign Up",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                TextButton(
                    onClick = {
                        authViewModel.onEvents(AuthEvents.OnNavSignIn)
                    }
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Light,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            ) {
                                append("Already have an account? ")

                            }

                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            ) {
                                append("Log in")
                            }
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun GoogleSignInButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_google),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "Sign In With Google",
            color = Color.White,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@Composable
private fun RegisterHeader() {
    Text(
        text = "Create An Account",
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "Enter your personal data to create an account",
        style = MaterialTheme.typography.bodyMedium,
        color = Color.White
    )
}


@Preview
@Composable
private fun RegisterPreview() {
    TimizaTheme {
        SignInScreen(
            onNavigate = {

            },
            popBackStack = {},
            authViewModel = TODO(),
            scope = rememberCoroutineScope(),
            snackbarHostState = TODO(),
            activity = TODO()
        )
    }
}