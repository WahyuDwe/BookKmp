package com.dwe.bookkmp

import androidx.compose.ui.window.ComposeUIViewController
import com.dwe.bookkmp.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initializeKoin()
    }
) { App() }