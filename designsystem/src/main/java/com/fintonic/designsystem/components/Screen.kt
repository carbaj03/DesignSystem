package com.fintonic.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.AppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy
import com.fintonic.designsystem.components.text.Text
import com.fintonic.designsystem.components.toolbar.ItemBack
import com.fintonic.designsystem.components.toolbar.ItemClose
import com.fintonic.designsystem.components.toolbar.ItemRight
import com.fintonic.designsystem.components.toolbar.Surface
import com.fintonic.designsystem.components.toolbar.Toolbar
import com.fintonic.designsystem.foundation.AppColor
import com.fintonic.designsystem.foundation.AppTheme
import com.fintonic.designsystem.foundation.appTypography

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    title: String? = null,
    onBack: (() -> Unit)? = null,
    onClose: (() -> Unit)? = null,
    itemRight: ItemRight? = null,
    backgroundColor: AppColor = AppTheme.colors.background,
    contentColor: AppColor = AppTheme.colors.onBackground,
    snackBarState: SnackBarState? = null,
    isLoading: Boolean = false,
    bottomBar: (@Composable RowScope.() -> Unit)? = null,
    scrollState: ScrollState = rememberScrollState(),
    content: @Composable (PaddingValues) -> Unit,
) {
    val elevationDefault by remember {
        derivedStateOf {
            if (scrollState.value == 0) 0.dp
            else AppBarDefaults.TopAppBarElevation
        }
    }
    val elevation by animateDpAsState(elevationDefault)
    
    Surface(
        modifier = modifier,
        color = backgroundColor,
        contentColor = contentColor
    ) {
        ScaffoldLayout(
            topBar = {
                Toolbar(
                    title = title,
                    itemLeft = {
                        onBack?.let { ItemBack(onClick = it) } ?: onClose?.let { ItemClose(onClick = it) }
                    },
                    itemRight = itemRight,
                    elevation = elevation
                )
            },
            snackbar = {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier.fillMaxSize()
                ) {
                    AnimatedVisibility(
                        visible = snackBarState is SnackBarState.Show,
                        enter = slideInVertically { height -> height },
                        exit = slideOutVertically { height -> height }
                    ) {
                        Row(
                            Modifier
                                .background(color = snackBarState?.color?.color ?: AppColor.Gray70.color)
                                .padding(20.dp)
                                .fillMaxWidth()
                        ) {
                            Text(style = appTypography.bodyM, text = snackBarState?.text ?: "")
                        }
                    }
                }
            },
            bottomBar = {
                bottomBar?.let {
                    MainBottomBar(modifier = Modifier, contentColor = contentColor, backgroundColor = backgroundColor, content = it)
                }
            },
            
            content = {
                content(PaddingValues(horizontal = 16.dp))
                if (isLoading) Loader()
            },
        )
    }
}

sealed class SnackBarState(open val text: String, open val color: AppColor) {
    data class Show(override val text: String, override val color: AppColor) : SnackBarState(text, color)
    data class Dismiss(override val text: String, override val color: AppColor) : SnackBarState(text, color)
}

@Composable
internal fun MainBottomBar(
    modifier: Modifier,
    contentColor: AppColor,
    backgroundColor: AppColor,
    content: @Composable (RowScope) -> Unit
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        elevation = 8.dp,
        shape = RectangleShape,
        modifier = modifier
    ) {
        CompositionLocalProvider() {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(56.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

private enum class ScaffoldLayoutContent {
    TopBar, MainContent, Sheet, Snackbar, BottomBar
}

@Composable
private fun ScaffoldLayout(
    topBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    snackbar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
) {
    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight
        
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        
        layout(layoutWidth, layoutHeight) {
            val topBarPlaceables = subcompose(ScaffoldLayoutContent.TopBar, topBar).fastMap {
                it.measure(looseConstraints)
            }
            
            val topBarHeight = topBarPlaceables.fastMaxBy { it.height }?.height ?: 0
            
            val snackbarPlaceables = subcompose(ScaffoldLayoutContent.Snackbar, snackbar).fastMap {
                it.measure(looseConstraints)
            }
            
            val snackbarHeight = snackbarPlaceables.fastMaxBy { it.height }?.height ?: 0
            
            val bottomBarPlaceables = subcompose(ScaffoldLayoutContent.BottomBar) {
                CompositionLocalProvider(content = bottomBar)
            }.fastMap { it.measure(looseConstraints) }
            
            val bottomBarHeight = bottomBarPlaceables.fastMaxBy { it.height }?.height ?: 0
            
            val snackbarOffsetFromBottom = if (snackbarHeight != 0) {
                snackbarHeight + bottomBarHeight
            } else {
                0
            }
            
            val bodyContentHeight = layoutHeight - topBarHeight
            
            val bodyContentPlaceables = subcompose(ScaffoldLayoutContent.MainContent) {
                val innerPadding = PaddingValues(bottom = bottomBarHeight.toDp())
                content(innerPadding)
            }.fastMap {
                it.measure(looseConstraints.copy(maxHeight = bodyContentHeight))
            }
            
            // Placing to control drawing order to match default elevation of each placeable
            bodyContentPlaceables.fastForEach {
                it.place(0, topBarHeight)
            }
            topBarPlaceables.fastForEach {
                it.place(0, 0)
            }
            snackbarPlaceables.fastForEach {
                it.place(0, layoutHeight - snackbarOffsetFromBottom)
            }
            // The bottom bar is always at the bottom of the layout
            bottomBarPlaceables.fastForEach {
                it.place(0, layoutHeight - bottomBarHeight)
            }
        }
    }
}