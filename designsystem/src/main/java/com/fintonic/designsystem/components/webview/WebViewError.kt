package com.fintonic.designsystem.components.webview

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import androidx.compose.runtime.Immutable

@Immutable
data class WebViewError(
    val request: WebResourceRequest?,
    val error: WebResourceError,
)