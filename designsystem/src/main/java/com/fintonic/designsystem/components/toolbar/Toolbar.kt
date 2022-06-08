package com.fintonic.designsystem.components.toolbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fintonic.designsystem.components.text.Text
import com.fintonic.designsystem.foundation.AppTheme
import com.fintonic.designsystem.foundation.appTypography

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    itemLeft: ItemLeft? = null,
    itemRight: ItemRight? = null,
    elevation: Dp = ToolbarElevation,
) {

    Surface(
        modifier = modifier,
        color = AppTheme.colors.toolbar,
        elevation = elevation,
        contentColor = AppTheme.colors.onToolbar,
        shape = RectangleShape,
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(ContentPadding)
                    .height(ToolbarHeight),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                itemLeft?.let { it() }

                Spacer(modifier = Modifier.widthIn(8.dp))

                Column {
                    title?.let {
                        Text(text = it, style = appTypography.bodyM, color = AppTheme.colors.onToolbar)
                    }

                    subtitle?.let {
                        Text(text = it, style = appTypography.bodyM, color = AppTheme.colors.onToolbar)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                itemRight?.let { Row { it(this) } }
            }
        }
    }
}

private val ToolbarHeight = 56.dp
private val ToolbarElevation = 4.dp
private val ToolbarHorizontalPadding = 4.dp

val ContentPadding = PaddingValues(
    start = ToolbarHorizontalPadding,
    end = ToolbarHorizontalPadding
)


typealias ItemLeft = (@Composable () -> Unit)
typealias ItemRight = (@Composable (RowScope) -> Unit)