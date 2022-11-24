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
    Font(R.font.cerebri_sans_bold, FontWeight.Bold),
    Font(R.font.cerebri_sans_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.cerebri_sans_book, FontWeight.Book),
    Font(R.font.cerebri_sans_book_italic, FontWeight.Book, FontStyle.Italic),
    Font(R.font.cerebri_sans_extra_bold, FontWeight.ExtraBold),
    Font(R.font.cerebri_sans_extra_bold_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.cerebri_sans_heavy, FontWeight.Heavy),
    Font(R.font.cerebri_sans_heavy_italic, FontWeight.Heavy, FontStyle.Italic),
    Font(R.font.cerebri_sans_light, FontWeight.Light),
    Font(R.font.cerebri_sans_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.cerebri_sans_medium, FontWeight.Medium),
    Font(R.font.cerebri_sans_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.cerebri_sans_semi_bold, FontWeight.SemiBold),
    Font(R.font.cerebri_sans_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.cerebri_sans_regular, FontWeight.Regular),
    Font(R.font.cerebri_sans_italic, FontWeight.Regular, FontStyle.Italic),
)

val FontWeight.Companion.Regular get() = W400
val FontWeight.Companion.Book get() = FontWeight(450)
val FontWeight.Companion.Heavy get() = W900

val defaultLetterSpacing = (-0.7).sp
val defaultLineHeight = 16.sp

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
            fontSize = 12.sp,
            lineHeight = defaultLineHeight,
            letterSpacing = defaultLetterSpacing,
        ),
        bodyS = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.Regular,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = defaultLetterSpacing,
            color = defaultBodyColor
        ),
        bodyM = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.Regular,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            letterSpacing = defaultLetterSpacing,
            color = defaultBodyColor
        ),
        bodyL = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.Regular,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            letterSpacing = defaultLetterSpacing,
            color = defaultBodyColor
        ),
        heading2XS = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            letterSpacing = defaultLetterSpacing,
            color = defaultHeadingColor
        ),
        headingXS = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            letterSpacing = defaultLetterSpacing,
            color =  defaultHeadingColor
        ),
        headingS = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = defaultLetterSpacing,
            color =  defaultHeadingColor
        ),
        headingM = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.Medium,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = defaultLetterSpacing,
            color =  defaultHeadingColor
        ),
        headingL = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 40.sp,
            lineHeight = 48.sp,
            letterSpacing = defaultLetterSpacing,
            color =  defaultHeadingColor
        ),
        headingXL = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 48.sp,
            lineHeight = 54.sp,
            letterSpacing = defaultLetterSpacing,
            color =  defaultHeadingColor
        ),
        heading2XL = TextStyle(
            fontFamily = primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 64.sp,
            lineHeight = 64.sp,
            letterSpacing = defaultLetterSpacing,
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