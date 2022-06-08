package com.fintonic.designsystem.foundation

import androidx.compose.material.Colors
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.memberProperties


@JvmInline
value class AppColor private constructor(val color: Color) {
    companion object {
        val Transparent = AppColor(Color(0x00FFFFFF))
        val White = AppColor(Color(0xFFFFFFFF))
        val Black = AppColor(Color(0xFF000000))
        val Gray10 = AppColor(Color(0xFFF3F6F9))
        val Gray20 = AppColor(Color(0xFFE4EBF2))
        val Gray30 = AppColor(Color(0xFFCFDAE6))
        val Gray40 = AppColor(Color(0xFFBFCEDE))
        val Gray50 = AppColor(Color(0xFFAFC1D5))
        val Gray60 = AppColor(Color(0xFF99AFC7))
        val Gray70 = AppColor(Color(0xFF929FAD))
        val Gray80 = AppColor(Color(0xFF6D7F92))
        val Gray90 = AppColor(Color(0xFF4B5D72))
        val Gray100 = AppColor(Color(0xFF243A68))

        val Coral10 = AppColor(Color(0xFFFFD5D7))
        val Coral30 = AppColor(Color(0xFFFE757A))
        val Coral = AppColor(Color(0xFFFE666B))
        val Coral70 = AppColor(Color(0xFFE8595E))

        val Blue10 = AppColor(Color(0xFFCCDEFF))
        val Blue30 = AppColor(Color(0xFF388BFF))
        val Blue = AppColor(Color(0xFF1F7CFF))
        val Blue70 = AppColor(Color(0xFF0066F5))

        val Green10 = AppColor(Color(0xFFDBF5E6))
        val Green30 = AppColor(Color(0xFF0ED861))
        val Green = AppColor(Color(0xFF0CC057))
        val Green70 = AppColor(Color(0xFF0BA34A))

        val Orange10 = AppColor(Color(0xFFFDEECF))
        val Orange30 = AppColor(Color(0xFFFFD780))
        val Orange = AppColor(Color(0xFFFFC442))
        val Orange70 = AppColor(Color(0xFFFF8400))
    }
}

suspend fun getColors(): List<Pair<String, AppColor>> =
    getProperties()

suspend inline fun <reified A> getProperties(): List<Pair<String, A>> = coroutineScope {
    delay(300)
    val a = A::class.companionObject?.objectInstance
    A::class.companionObject?.memberProperties?.map {
        it.name to it.call(a) as A
    } ?: listOf()
}

//@Composable
//@ReadOnlyComposable
//fun contentColorFor(backgroundColor: AppColor) =
//    AppTheme.colors.contentColorFor(backgroundColor).takeOrElse { LocalContentColor.current }
//
//fun AppColors.contentColorFor(backgroundColor: AppColor): AppColor {
//    return when (backgroundColor) {
//        primary -> onPrimary
//        primaryVariant -> onPrimary
//        secondary -> onSecondary
//        secondaryVariant -> onSecondary
//        background -> onBackground
//        surface -> onSurface
//        error -> onError
//        else -> Color.Unspecified
//    }
//}


data class AppColors(
    val primary: AppColor,
    val onPrimary: AppColor,
    val toolbar: AppColor,
    val onToolbar: AppColor,
    val toolbarItemRight: AppColor,
    val toolbarItemLeft: AppColor,
    val background: AppColor,
    val onBackground: AppColor,
    val surface: AppColor,
    val onSurface: AppColor,
    val isLight: Boolean
)

data class ButtonColors(
    val primary: ButtonColor,
    val secondary: ButtonColor,
    val tertiary: ButtonColor,
)

data class ButtonColor(
    val enabled: AppColor,
    val disabled: AppColor,
    val onEnabled: AppColor,
    val onDisabled: AppColor,
)

operator fun ButtonColor.invoke(isEnabled: Boolean): AppColor =
    if (isEnabled) enabled else disabled

fun ButtonColor.colorFor(isEnabled: Boolean): AppColor =
    if (isEnabled) onEnabled else onDisabled


val light = AppColors(
    primary = AppColor.White,
    onPrimary = AppColor.Black,
    toolbar = AppColor.White,
    onToolbar = AppColor.Gray100,
    background = AppColor.White,
    onBackground = AppColor.Gray100,
    toolbarItemRight = AppColor.Blue30,
    toolbarItemLeft = AppColor.Gray100,
    surface = AppColor.White,
    onSurface = AppColor.Black,
    isLight = true
)

val lightButtons: ButtonColors =
    ButtonColors(
        primary = ButtonColor(
            enabled = AppColor.Blue,
            disabled = AppColor.Gray10,
            onEnabled = AppColor.White,
            onDisabled = AppColor.Gray70
        ),
        secondary = ButtonColor(
            enabled = AppColor.White,
            disabled = AppColor.White,
            onEnabled = AppColor.Blue,
            onDisabled = AppColor.Gray70
        ),
        tertiary = ButtonColor(
            enabled = AppColor.Transparent,
            disabled = AppColor.Transparent,
            onEnabled = AppColor.Blue,
            onDisabled = AppColor.Gray70
        ),
    )

val dark = AppColors(
    primary = AppColor.Black,
    onPrimary = AppColor.White,
    toolbar = AppColor.Black,
    onToolbar = AppColor.White,
    background = AppColor.Black,
    onBackground = AppColor.White,
    toolbarItemRight = AppColor.White,
    toolbarItemLeft = AppColor.White,
    surface = AppColor.Black,
    onSurface = AppColor.White,
    isLight = false
)

val LocalColors = staticCompositionLocalOf { light }
val LocalButtonColors = staticCompositionLocalOf { lightButtons }