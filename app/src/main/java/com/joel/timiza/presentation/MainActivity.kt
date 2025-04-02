package com.joel.timiza.presentation

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.joel.timiza.presentation.auth.AuthViewModel
import com.joel.timiza.presentation.auth.SplashViewModel
import com.joel.timiza.presentation.navigation.BottomNavigationBar
import com.joel.timiza.presentation.navigation.TimizaNavGraph
import com.joel.timiza.ui.theme.TimizaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
            val snackbarHostState = SnackbarHostState()
            val context = LocalContext.current
            val activity = context as? Activity
            val authViewModel = hiltViewModel<AuthViewModel>()

            installSplashScreen().setKeepOnScreenCondition {
                !authViewModel.isLoading.value
            }

            val screen by authViewModel.startDestination
            
            TimizaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { if (bottomBarState.value) BottomNavigationBar(navController) },
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    containerColor = Color(0xFF142036)
                ) { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                    ) {
                        TimizaNavGraph(
                            navHostController = navController,
                            updateBottomBarState = { bottomBarState.value = it },
                            activity = activity!!,
                            startDestinations = screen
                        )
                    }
                }
            }
        }
    }
}

