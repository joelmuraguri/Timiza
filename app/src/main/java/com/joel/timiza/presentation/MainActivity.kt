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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.joel.timiza.presentation.navigation.BottomNavigationBar
import com.joel.timiza.presentation.navigation.TimizaNavGraph
import com.joel.timiza.ui.theme.TimizaTheme
import dagger.hilt.android.AndroidEntryPoint

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
                        TimizaNavGraph (
                            navHostController = navController,
                            updateBottomBarState = { bottomBarState.value = it },
                            activity = activity!!
                        )
                    }
                }
            }
        }
    }
}

