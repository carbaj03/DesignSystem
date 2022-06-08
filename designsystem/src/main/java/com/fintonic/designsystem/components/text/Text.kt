package com.fintonic.designsystem.components.text

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.fintonic.designsystem.foundation.AppColor
import com.fintonic.designsystem.foundation.AppTheme
import com.fintonic.designsystem.foundation.LocalTextStyle

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: AppColor = AppTheme.colors.onPrimary,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    val mergedStyle = style.merge(
        TextStyle(
            color = color.color,
            fontSize = style.fontSize,
            fontWeight = style.fontWeight,
            textAlign = textAlign,
            lineHeight = lineHeight,
            fontFamily = style.fontFamily,
            textDecoration = textDecoration,
            fontStyle = style.fontStyle,
            letterSpacing = style.letterSpacing
        )
    )
    BasicText(
        text,
        modifier,
        mergedStyle,
        onTextLayout,
        overflow,
        softWrap,
        maxLines,
    )
}
