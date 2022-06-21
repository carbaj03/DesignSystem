package com.fintonic.designsystem.foundation

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Shapes
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.fintonic.designsystem.foundation.*

@Composable
fun DesignSystemTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    AppTheme(
        colors = if (darkTheme) light else dark,
        buttonColors = lightButtons,
        typography = appTypography,
        shapes = Shapes,
        content = content
    )
}


@Composable
fun AppTheme(
    colors: AppColors = AppTheme.colors,
    buttonColors: ButtonColors = AppTheme.buttonColors,
    typography: AppTypography = AppTheme.typography,
    shapes: Shapes = AppTheme.shapes,
    rippleIndication: Indication = rememberRipple(),
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalButtonColors provides buttonColors,
        LocalIndication provides rippleIndication,
//        LocalRippleTheme provides MaterialRippleTheme,
        LocalShapes provides shapes,
        LocalTypography provides typography
    ) {
        ProvideTextStyle(value = typography.bodyM) {
            content()
//            MaterialTheme(
//                colors = colors.material(),
//                typography = typography.material(),
//                shapes = shapes,
//                content = content
//            )
        }
    }
}


