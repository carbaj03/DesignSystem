package com.fintonic.designsystem.components

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.fintonic.designsystem.components.toolbar.Surface
import com.fintonic.designsystem.foundation.AppColor
import com.fintonic.designsystem.foundation.DesignSystemTheme

fun ComponentActivity.template(
    parent: CompositionContext? = null,
    enabledDarkTheme: Boolean = false,
    content: @Composable ((Boolean) -> Unit) -> Unit,
) {
    setContent(parent) {
        val isDarkTheme = isSystemInDarkTheme()
        var mode by remember { mutableStateOf(if (enabledDarkTheme) isDarkTheme else false) }
        
        DesignSystemTheme(mode) {
            Surface(color = AppColor.Blue, elevation = 20.dp) {
                content { mode = it }
            }
        }
    }
}

fun ComposeView.template(
    enabledDarkTheme: Boolean = false,
    content: @Composable ((Boolean) -> Unit) -> Unit,
) {
    setContent {
        val isDarkTheme = isSystemInDarkTheme()
        var mode by remember { mutableStateOf(if (enabledDarkTheme) isDarkTheme else false) }
        
        DesignSystemTheme(mode) {
            Surface(color = AppColor.Blue, elevation = 20.dp) {
                content { mode = it }
            }
        }
    }
}