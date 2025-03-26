package com.joel.timiza.utils

import com.joel.timiza.presentation.navigation.Destinations

sealed class TimizaEvents {
    data class Navigate(val route : Destinations) : TimizaEvents()
    data object PopBackStack : TimizaEvents()
    data class ShowSnackbar(
        val message: String,
        val actionLabel: String? = null,
        val onActionClick: (() -> Unit)? = null ) : TimizaEvents()

}