package com.fintonic.designsystem

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fintonic.designsystem.components.Loader
import com.fintonic.designsystem.components.button.ButtonPrimary
import com.fintonic.designsystem.components.button.ButtonSecondary
import com.fintonic.designsystem.components.button.ButtonTertiary
import com.fintonic.designsystem.components.input.InputText
import com.fintonic.designsystem.components.input.SubText
import com.fintonic.designsystem.components.text.Text
import com.fintonic.designsystem.components.toolbar.*
import com.fintonic.designsystem.foundation.*
import com.fintonic.designsystem.ui.theme.DesignSystemTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        template { mode ->
            val navController = rememberNavController()
            val scope = rememberCoroutineScope()

            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    MainScreen(mode, navController)
                }
                composable("color") {
                    ColorScreen(
                        onBack = { navController.popBackStack() },
                        colors = LaunchEffect(initial = emptyList()) { getColors() })
                }
                composable("typography") {
                    TypographyScreen(
                        onBack = { navController.popBackStack() },
                        typographys = LaunchEffect(initial = emptyList()) { getFonts(appTypography) }
                    )
                }
                composable("button") {
                    var state: SnackBarState? by remember { mutableStateOf(null) }
                    var isLoading by remember {
                        mutableStateOf(false)
                    }

                    LaunchedEffect(key1 = null, block = { delay(2000); state = SnackBarState.Show("asfd"); delay(2000); state = null })
                    LaunchedEffect(key1 = null, block = { isLoading = true; delay(2000); isLoading = false })

                    ButtonScreen(onBack = { navController.popBackStack() }, state = state, isLoading = isLoading)
                }
                composable("input") {
                    InputScreen(onBack = { navController.popBackStack() })
                }
            }
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun <A> LaunchEffect(initial: A, action: suspend () -> A): A {
    var colors: A by remember { mutableStateOf(initial) }
    LaunchedEffect(null) { colors = action() }
    return colors
}

@Composable
fun MainScreen(mode: (Boolean) -> Unit, navController: NavHostController) {

    Screen(
        title = "pepe",
        itemRight = {
            Text(
                text = "L",
                modifier = Modifier.clickable { mode(false) },
                color = AppTheme.colors.toolbarItemRight
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "D",
                modifier = Modifier.clickable { mode(true) },
                color = AppTheme.colors.toolbarItemRight
            )
            Spacer(modifier = Modifier.width(16.dp))
        },
        bottomBar = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Favorite, "")
            }
        }
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(bottom = it.calculateBottomPadding())
                .background(AppTheme.colors.background.color),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                MyCard(onClick = { navController.navigate("button") }) {
                    Text(text = "Button", style = appTypography.bodyM, color = AppColor.Black)
                }
            }
            item {
                MyCard(onClick = { navController.navigate("typography") }) {
                    Text(text = "typography", style = appTypography.bodyM, color = AppColor.Black)
                }
            }
            item {
                MyCard(onClick = { navController.navigate("input") }) {
                    Text(text = "Input", style = appTypography.bodyM, color = AppColor.Black)
                }
            }
            item {
                MyCard(onClick = { navController.navigate("color") }) {
                    Text(text = "Color", style = appTypography.bodyM, color = AppColor.Black)
                }
            }
        }
    }
}

@Composable
fun MyCard(onClick: () -> Unit, content: @Composable (BoxScope) -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(color = AppColor.White.color, RoundedCornerShape(4.dp))
            .border(BorderStroke(0.5.dp, AppColor.Coral.color), RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
            .padding(8.dp),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@Composable
fun InputScreen(onBack: (() -> Unit)) {
    Screen(
        title = "Buttons",
        onBack = onBack
    ) {
        var text by remember { mutableStateOf("") }
        var text1 by remember { mutableStateOf("") }

        InputText(
            text = text,
            onTextChange = { text = it },
            placeholder = "sadfs",
            subText = SubText.Info("fadsf")
        )
        InputText(
            text = text1,
            onTextChange = { text1 = it },
            placeholder = "sadfs"
        )
    }
}


sealed class SnackBarState(open val text: String) {
    data class Show(override val text: String) : SnackBarState(text)
    data class Dismiss(override val text: String) : SnackBarState(text)
}

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
    bottomBar: (@Composable (RowScope) -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {

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
                        onBack?.let { ItemBack(onBack = it) } ?: onClose?.let { ItemClose(onClose = it) }
                    },
                    itemRight = itemRight
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
                                .background(color = AppColor.Green.color)
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
                content(PaddingValues(end = 16.dp, start = 16.dp, top = 16.dp, bottom = it.calculateBottomPadding()))
                if (isLoading) Loader()
            },
        )
    }
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

private enum class ScaffoldLayoutContent { TopBar, MainContent, Snackbar, Fab, BottomBar }

@Composable
private fun ScaffoldLayout(
    topBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    snackbar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
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

@Composable
fun ButtonScreen(onBack: (() -> Unit), state: SnackBarState?, isLoading: Boolean) {
    Screen(
        title = "Buttons",
        onBack = onBack,
        snackBarState = state,
        isLoading = isLoading
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            ButtonPrimary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, iconRight = R.drawable.ic_info)
            SpacerVertical(10.dp)
            ButtonPrimary(onClick = { /*TODO*/ }, iconRight = R.drawable.ic_info)
            SpacerVertical(10.dp)
            ButtonPrimary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_info)
            SpacerVertical(10.dp)
            ButtonPrimary(onClick = { /*TODO*/ })
            SpacerVertical(10.dp)

            ButtonPrimary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, iconRight = R.drawable.ic_info, enabled = false)
            SpacerVertical(10.dp)
            ButtonPrimary(onClick = { /*TODO*/ }, iconRight = R.drawable.ic_info, enabled = false)
            SpacerVertical(10.dp)
            ButtonPrimary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, enabled = false)
            SpacerVertical(10.dp)
            ButtonPrimary(onClick = { /*TODO*/ }, enabled = false)
            SpacerVertical(10.dp)

            ButtonSecondary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, iconRight = R.drawable.ic_info)
            SpacerVertical(10.dp)
            ButtonSecondary(onClick = { /*TODO*/ }, iconRight = R.drawable.ic_info)
            SpacerVertical(10.dp)
            ButtonSecondary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_info)
            SpacerVertical(10.dp)
            ButtonSecondary(onClick = { /*TODO*/ })
            SpacerVertical(10.dp)

            ButtonSecondary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, iconRight = R.drawable.ic_info, enabled = false)
            SpacerVertical(10.dp)
            ButtonSecondary(onClick = { /*TODO*/ }, iconRight = R.drawable.ic_info, enabled = false)
            SpacerVertical(10.dp)
            ButtonSecondary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, enabled = false)
            SpacerVertical(10.dp)
            ButtonSecondary(onClick = { /*TODO*/ }, enabled = false)
            SpacerVertical(10.dp)

            ButtonTertiary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, iconRight = R.drawable.ic_info)
            SpacerVertical(10.dp)
            ButtonTertiary(onClick = { /*TODO*/ }, iconRight = R.drawable.ic_info)
            SpacerVertical(10.dp)
            ButtonTertiary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_info)
            SpacerVertical(10.dp)
            ButtonTertiary(onClick = { /*TODO*/ })
            SpacerVertical(10.dp)

            ButtonTertiary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, iconRight = R.drawable.ic_info, enabled = false)
            SpacerVertical(10.dp)
            ButtonTertiary(onClick = { /*TODO*/ }, iconRight = R.drawable.ic_info, enabled = false)
            SpacerVertical(10.dp)
            ButtonTertiary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, enabled = false)
            SpacerVertical(10.dp)
            ButtonTertiary(onClick = { /*TODO*/ }, enabled = false)
            SpacerVertical(10.dp)
        }
    }
}

@Composable
fun SpacerVertical(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun SpacerHorizontal(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun TypographyScreen(
    typographys: List<Pair<String, TextStyle>>,
    onBack: (() -> Unit)
) {
    Screen(
        title = "Buttons",
        onBack = onBack
    ) {
        LazyColumn(content = {
            items(typographys) {
                Text(style = it.second, text = it.first)
            }
        })
//        Column(Modifier.padding(it)) {
//            Text(style = appTypography.detail, text = "detail")
//            Text(style = appTypography.bodyS, text = "BodyS")
//            Text(style = appTypography.bodyM, text = "BodyM")
//            Text(style = appTypography.bodyL, text = "BodyL")
//            Text(style = appTypography.heading2XS, text = "headingXL")
//            Text(style = appTypography.headingXS, text = "headingXL")
//            Text(style = appTypography.headingS, text = "headingXL")
//            Text(style = appTypography.headingM, text = "headingM")
//            Text(style = appTypography.headingL, text = "headingXL")
//            Text(style = appTypography.headingXL, text = "headingXL")
//            Text(style = appTypography.heading2XL, text = "headingXL")
//        }
    }
}

@Composable
fun ColorScreen(
    colors: List<Pair<String, AppColor>>,
    onBack: () -> Unit
) {
    Screen(
        title = "Buttons",
        onBack = onBack
    ) {
        LazyColumn(Modifier.padding(it)) {
            items(colors) {
                CardColor(text = it.first, it.second)
            }
        }
    }
}

@Composable
fun CardColor(text: String, second: AppColor) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(second.color, shape = RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(style = appTypography.bodyM, text = text)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DesignSystemTheme {
        Greeting("Android")
    }
}

public fun ComponentActivity.template(
    parent: CompositionContext? = null,
    content: @Composable ((Boolean) -> Unit) -> Unit
) {
    setContent(parent) {
        var mode by remember { mutableStateOf(false) }

        DesignSystemTheme(mode) {
            Surface(color = AppColor.Blue, elevation = 20.dp) {
                content { mode = it }
            }
        }
    }
}
