package com.kmp.newsapp

import androidx.compose.ui.window.ComposeUIViewController
import com.kmp.newsapp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }