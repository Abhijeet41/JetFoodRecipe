package com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.tabs

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun InstructionScreen(sourceUrl: String?) {
    Scaffold(
        content = {
            WebviewContent(sourceUrl)
        }
    )
}

@Composable
fun WebviewContent(sourceUrl: String?) {
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(sourceUrl ?: "null")
        }
    }, update = {
        it.loadUrl(sourceUrl ?: "null")
    }, modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    )
}
