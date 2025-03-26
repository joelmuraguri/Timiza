package com.joel.timiza.presentation.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.joel.timiza.ui.theme.black
import com.joel.timiza.ui.theme.darkPurple
import com.joel.timiza.ui.theme.purple

@Composable
fun Gradient() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.35f)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        purple,
                        darkPurple,
                        black
                    )
                )
            )
    )
}