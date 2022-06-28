package com.fintonic.designsystem.components.text

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun BoldSpanned(
    modifier: Modifier = Modifier,
    style: TextStyle,
    text: String,
    vararg textInBold: String
) {
    if (textInBold.none { text.contains(it) }) {
        Text(
            modifier = modifier,
            text = text,
            style = style
        )
    } else {
        BasicText(
            modifier = modifier,
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

                textInBold.forEach {
                    val first = text.indexOf(it)
                    if (first != -1) {
                        addStyle(
                            style = SpanStyle(
                                color = merge.color,
                                fontFamily = merge.fontFamily,
                                fontSize = merge.fontSize,
                                fontWeight = FontWeight.Bold
                            ),
                            start = first,
                            end = first + it.length
                        )
                    }
                }
            }
        )
    }
}