package com.joel.timiza.presentation.navigation

import android.app.Activity
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.joel.timiza.R
import com.joel.timiza.domain.models.Status
import com.joel.timiza.domain.models.Todo
import com.joel.timiza.domain.models.User
import com.joel.timiza.presentation.auth.SignInScreen
import com.joel.timiza.presentation.auth.SignUpScreen
import com.joel.timiza.presentation.edit.EditTodosScreen
import com.joel.timiza.presentation.list.TodosScreen
import com.joel.timiza.presentation.uploads.UploadsScreen
import com.joel.timiza.ui.theme.TimizaTheme
import kotlinx.serialization.Serializable

@Composable
fun TimizaNavGraph(
    navHostController: NavHostController,
    updateBottomBarState: (Boolean) -> Unit,
    onSignInClick: () -> Unit,
    activity: Activity
){

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    NavHost(
        navController = navHostController,
        startDestination = Destinations.SignIn
    ){
        
        composable<Destinations.SignIn> {
            updateBottomBarState(false)
            SignInScreen(
                onNavigate = {
                    navHostController.navigate(it.route)
                },
                popBackStack = {
                    navHostController.popBackStack()
                },
                scope = scope,
                snackbarHostState = snackbarHostState, activity = activity
            )
        }
        composable<Destinations.SignUp> {
            updateBottomBarState(false)
            SignUpScreen(
                onNavigate = {
                    navHostController.navigate(it.route)
                },
                popBackStack = {
                    navHostController.popBackStack()
                },
                scope = scope,
                snackbarHostState = snackbarHostState, activity = activity
            )
        }
        composable<Destinations.TodoList> {
            updateBottomBarState(true)
            TodosScreen(
                user = User(
                    "", "Joel", "joel@gmail.com"
                ),
                onNavigate = { events ->
                    navHostController.navigate(events.route)
                },
                popBackStack = {
                    navHostController.popBackStack()
                },
            )
        }
        composable<Destinations.TodoEdit> {
            updateBottomBarState(false)
            EditTodosScreen(
                todo = Todo(
                    id = 1,
                    title = "Assignments",
                    content = "My first todo assignment",
                    status = Status.PENDING
                )
            )
        }
        composable<Destinations.Uploads> {
            updateBottomBarState(true)
            UploadsScreen()
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomAppBar (
        containerColor = Color(0xFF132847)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        BottomNavigation.entries.forEach { destination ->
//            val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
            val isSelected = currentDestination?.hierarchy?.any { it.route == destination.route::class.qualifiedName } == true

            NavigationBarItem(
                selected = isSelected,
                icon = {
                    Icon(
                        painter = painterResource(destination.icon),
                        contentDescription = destination.label,
                        tint = if (isSelected) Color(0xFFFEB800) else Color(0xFF6C788E),
                        modifier = Modifier
                            .size(25.dp)
                    )
                },
                label = {
                    Text(
                        text = destination.label,
                        color = if (isSelected) Color(0xFFFEB800) else Color(0xFF6C788E)
                    ) },
                alwaysShowLabel = true,
                onClick = {
                    navController.navigate(destination.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(Destinations.TodoList){
                            inclusive = true
                            saveState = true
                        }
                    }
                },
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    TimizaTheme {
        BottomNavigationBar(rememberNavController())
    }
}

sealed class Destinations {
    @Serializable
    data object SignUp : Destinations()
    @Serializable
    data object SignIn : Destinations()
    @Serializable
    data object TodoList : Destinations()
    @Serializable
    data object TodoEdit : Destinations()
    @Serializable
    data object Uploads : Destinations()
}

enum class BottomNavigation(val label: String, val icon: Int, val route: Destinations) {
    TODO("Todo", R.drawable.arcticons__pomotodo, Destinations.TodoList),
    UPLOADS("Uploads", R.drawable.carbon__volume_file_storage, Destinations.Uploads),
}
