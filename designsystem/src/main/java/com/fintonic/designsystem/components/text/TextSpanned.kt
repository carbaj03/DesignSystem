package com.fintonic.designsystem.components.text

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.fintonic.designsystem.foundation.AppColor
import com.fintonic.designsystem.foundation.AppTheme


data class TextSpannedStyle(
    val text: String,
    val textStyle: TextStyle,
    val color: Color = textStyle.color,
    val onClick: (String) -> Unit = {}
)

@Composable
fun TextSpanned(
    vararg textWithStyle: TextSpannedStyle,
    modifier: Modifier = Modifier,
    style: TextStyle,
    text: String,
    color: AppColor = AppTheme.colors.onPrimary,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    if (textWithStyle.none { text.contains(it.text) }) {
        Text(
            modifier = modifier,
            text = text,
            style = style,
            textAlign = textAlign,
            color = color,
            lineHeight = lineHeight,
            onTextLayout = onTextLayout,
            maxLines = maxLines,
            overflow = overflow,
            textDecoration = textDecoration,
            softWrap = softWrap,
        )
    } else {
        val merge = style.merge(
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

        ClickableText(
            modifier = modifier,
            style = merge,
            onClick = { offset ->
                textWithStyle.forEach {
                    val first: Int = text.indexOf(it.text)
                    val last = first + it.text.length
                    if (offset in first..last) it.onClick(it.text)
                }
            },
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = merge.color,
                        fontFamily = merge.fontFamily,
                        fontSize = merge.fontSize,
                        fontWeight = merge.fontWeight,
                    )
                ) {
                    append(text)
                }

                textWithStyle.forEach {
                    val first = text.indexOf(it.text)
                    if (first != -1) {
                        addStyle(
                            style = SpanStyle(
                                color = it.color,
                                fontFamily = it.textStyle.fontFamily,
                                fontSize = it.textStyle.fontSize,
                                fontWeight = it.textStyle.fontWeight,
                                letterSpacing = it.textStyle.letterSpacing,
                            ),
                            start = first,
                            end = first + it.text.length
                        )
                    }
                }
            }
        )
    }
}