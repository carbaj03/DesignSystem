package com.fintonic.designsystem.foundation


import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fintonic.designsystem.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.reflect.full.memberProperties


val primary: FontFamily = FontFamily(
    Font(R.font.red_hat_display_bold, FontWeight.Bold),
    Font(R.font.red_hat_display_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.red_hat_display_extra_bold, FontWeight.ExtraBold),
    Font(R.font.red_hat_display_extra_bold_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.red_hat_display_black, FontWeight.Heavy),
    Font(R.font.red_hat_display_black_italic, FontWeight.Heavy, FontStyle.Italic),
    Font(R.font.red_hat_display_light, FontWeight.Light),
    Font(R.font.red_hat_display_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.red_hat_display_medium, FontWeight.Medium),
    Font(R.font.red_hat_display_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.red_hat_display_semi_bold, FontWeight.SemiBold),
    Font(R.font.red_hat_display_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.red_hat_display_regular, FontWeight.Regular),
    Font(R.font.red_hat_display_italic, FontWeight.Regular, FontStyle.Italic),
)

val FontWeight.Companion.Regular get() = W400
val FontWeight.Companion.Heavy get() = W900

val lowLetterSpacing = (-0.1).sp
val mediumLetterSpacing = (-0.3).sp
val highLetterSpacing = (-0.5).sp
val defaultLineHeight = 15.sp

internal val defaultBodyColor = AppColor.Gray100.color
internal val defaultHeadingColor = AppColor.Gray100.color


@Immutable
data class AppTypography(
    val detail: TextStyle,
    val bodyS: TextStyle,
    val bodyM: TextStyle,
    val bodyL: TextStyle,
    val heading2XS: TextStyle,
    val headingXS: TextStyle,
    val headingS: TextStyle,
    val headingM: TextStyle,
    val headingL: TextStyle,
    val headingXL: TextStyle,
    val heading2XL: TextStyle,
)

suspend inline fun <reified A, B> getFonts(a: A): List<Pair<String, B>> = coroutineScope {
    delay(300)
    a!!::class.memberProperties.map { it.name to it.call(a) as B }
}


val appTypography: AppTypography =
    AppTypography(
        detail = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.Regular,
            fontSize = 11.sp,
            lineHeight = defaultLineHeight,
            letterSpacing = lowLetterSpacing,
        ),
        bodyS = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.Regular,
            fontSize = 13.sp,
            lineHeight = 19.sp,
            letterSpacing = lowLetterSpacing,
            color = defaultBodyColor
        ),
        bodyM = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.Regular,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = lowLetterSpacing,
            color = defaultBodyColor
        ),
        bodyL = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.Regular,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            letterSpacing = lowLetterSpacing,
            color = defaultBodyColor
        ),
        heading2XS = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            letterSpacing = mediumLetterSpacing,
            color = defaultHeadingColor
        ),
        headingXS = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            letterSpacing = mediumLetterSpacing,
            color =  defaultHeadingColor
        ),
        headingS = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = mediumLetterSpacing,
            color =  defaultHeadingColor
        ),
        headingM = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.Medium,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = mediumLetterSpacing,
            color =  defaultHeadingColor
        ),
        headingL = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 40.sp,
            lineHeight = 48.sp,
            letterSpacing = highLetterSpacing,
            color =  defaultHeadingColor
        ),
        headingXL = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 48.sp,
            lineHeight = 54.sp,
            letterSpacing = highLetterSpacing,
            color =  defaultHeadingColor
        ),
        heading2XL = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 64.sp,
            lineHeight = 64.sp,
            letterSpacing = highLetterSpacing,
            color =  defaultHeadingColor
        ),
    )

val LocalTypography = staticCompositionLocalOf { appTypography }

val LocalTextStyle = compositionLocalOf(structuralEqualityPolicy()) { appTypography.bodyM }

@Composable
fun ProvideTextStyle(value: TextStyle, content: @Composable () -> Unit) {
    val mergedStyle = LocalTextStyle.current.merge(value)
    CompositionLocalProvider(LocalTextStyle provides mergedStyle, content = content)
}