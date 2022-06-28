package com.fintonic.designsystem.components.text

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle


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
) {
    if (textWithStyle.none { text.contains(it.text) }) {
        Text(
            modifier = modifier,
            text = text,
            style = style
        )
    } else {
        ClickableText(
            modifier = modifier,
            onClick = { offset ->
                textWithStyle.forEach {
                    val first: Int = text.indexOf(it.text)
                    val last = first + it.text.length
                    if(offset >= first && offset <= last) it.onClick(it.text)
                }
            },
            text = buildAnnotatedString {
                val merge = style.merge()

                withStyle(
                    style = SpanStyle(
                        color = merge.color,
                        fontFamily = merge.fontFamily,
                        fontSize = merge.fontSize,
                        fontWeight = merge.fontWeight
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