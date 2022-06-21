package com.fintonic.designsystem.components

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.fintonic.designsystem.components.toolbar.Surface
import com.fintonic.designsystem.foundation.AppColor
import com.fintonic.designsystem.foundation.DesignSystemTheme

fun ComponentActivity.template(
    parent: CompositionContext? = null,
    content: @Composable ((Boolean) -> Unit) -> Unit
) {
    setContent(parent) {
        var mode by remember { mutableStateOf(false) }

        DesignSystemTheme(mode) {
            Surface(color = AppColor.Blue, elevation = 20.dp) {
                content { mode = it }
            }
        }
    }
}