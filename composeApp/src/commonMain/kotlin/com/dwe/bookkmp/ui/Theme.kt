package com.dwe.bookkmp.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun BookTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = PoppinsTypography(),
        content = content
    )
}