package com.joel.timiza.presentation.list

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.joel.timiza.presentation.design.components.CategoryDialog
import com.joel.timiza.presentation.design.components.CustomTopBar
import com.joel.timiza.utils.TimizaEvents
import com.joel.timiza.R

@Composable
fun TodosScreen(
    onNavigate: (TimizaEvents.Navigate) -> Unit,
    popBackStack: () -> Unit,
    todoViewModel: TodoViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    var showProfileDropdown by remember { mutableStateOf(false) }
    var showCategoryDialog by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var categories by remember { mutableStateOf(listOf("Work", "Urgent")) }
    var newCategory by remember { mutableStateOf("") }

    val user = todoViewModel.user.value

    if (user != null) {
        Log.d("TodosScreen", "---------------------> userData ${user.email}")
        Log.d("TodosScreen", "---------------------> userData ${user.name}")
        Log.d("TodosScreen", "---------------------> userData ${user.profileUrl}")
    } else {
        Log.d("TodosScreen", "---------------------> No user data available")
    }


    LaunchedEffect(key1 = true) {
        todoViewModel.uiEvents.collect { timizaEvents ->
            when (timizaEvents) {
                is TimizaEvents.Navigate -> onNavigate(timizaEvents)
                TimizaEvents.PopBackStack -> popBackStack()
                is TimizaEvents.ShowSnackbar -> TODO()
            }
        }
    }

    Scaffold(
        topBar = {
            if (user != null) {
                CustomTopBar(
                    user = user,
                    isExpanded = showProfileDropdown,
                    onProfileClick = {
                        showProfileDropdown = it
                    },
                    onSearchClick = {

                    },
                    onSignOutClick = {

                    }
                ) {
                    CategoryChips(
                        categories = categories,
                        onCategoryAdded = {

                        },
                        showCategoryDialog = showCategoryDialog,
                        newCategory = newCategory
                    )
                }
            } else {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { todoViewModel.onEvents(TodoScreenEvent.OnAddTodo) }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(15.dp)
                ) {
                    Icon(Icons.Default.Add, null)
                    Text("Add Todo")
                }
            }
        }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text("TODOS")
        }
    }

}

@Composable
fun CategoryChips(
    categories: List<String>,
    onCategoryAdded: (String) -> Unit,
    showCategoryDialog : Boolean,
    newCategory : String
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }


    Column {
        LazyRow (
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                IconButton(onClick = { showDialog = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Category")
                }
            }
            items(categories) { category ->
                FilterChip(
                    selected = category == selectedCategory,
                    onClick = { selectedCategory = category },
                    label = { Text(category) }
                )
            }
        }
    }

    if (showCategoryDialog) {
        CategoryDialog(
            onDismissRequest = {},
            value = newCategory,
            onValueChange = {},
            onSaveCategory = {}
        )
    }
}


@Composable
fun TodosTopBarActions(
    showProfileDropdown : Boolean,
    onShowProfileDropdownChange : (Boolean) -> Unit,
    userName : String,
    profileUrl : String,
    email : String,
    onSignOut : () -> Unit,
    onSearchIcon : () -> Unit
){

    Row {
        IconButton(onClick = { onSearchIcon() }) {
            Icon(Icons.Default.Search, null)
        }
        Box {
            IconButton(onClick = { onShowProfileDropdownChange(true) }) {
                ReusableImageLoader(profileUrl)
            }
            DropdownMenu(
                expanded = showProfileDropdown,
                onDismissRequest = { onShowProfileDropdownChange(true) },
                modifier = Modifier.padding(8.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .width(200.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ReusableImageLoader(profileUrl)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(userName, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(email, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = {
                            onSignOut()
                        }) {
                            Text("Sign Out")
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun ReusableImageLoader(
    profileUrl: String,
){
    AsyncImage(
        model = profileUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.baseline_person_pin_24),
        error = painterResource(id = R.drawable.baseline_person_pin_24),
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
    )
}