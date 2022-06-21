package com.fintonic.designsystem

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fintonic.designsystem.components.Screen
import com.fintonic.designsystem.components.SnackBarState
import com.fintonic.designsystem.components.button.ButtonPrimary
import com.fintonic.designsystem.components.button.ButtonSecondary
import com.fintonic.designsystem.components.button.ButtonTertiary
import com.fintonic.designsystem.components.input.InputText
import com.fintonic.designsystem.components.input.SubText
import com.fintonic.designsystem.components.template
import com.fintonic.designsystem.components.text.Text
import com.fintonic.designsystem.components.toolbar.*
import com.fintonic.designsystem.foundation.*
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
//        bottomBar = {
//            IconButton(onClick = { /* doSomething() */ }) {
//                Icon(Icons.Filled.Favorite, "")
//            }
//        },
        sheetContent = {
            Text(text = "sadf")
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

        Column(Modifier.padding(16.dp)) {
            InputText(
                text = text,
                onTextChange = { text = it },
                placeholder = "sadfs",
                subText = SubText.Info("fadsf")
            )
            SpacerVertical(height = 20.dp)
            InputText(
                text = text1,
                onTextChange = { text1 = it },
                placeholder = "sadfs"
            )
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
            ButtonPrimary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, iconRight = R.drawable.ic_info, text = "Primary")
            SpacerVertical(10.dp)
            ButtonPrimary(onClick = { /*TODO*/ }, iconRight = R.drawable.ic_info, text = "Primary")
            SpacerVertical(10.dp)
            ButtonPrimary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_info, text = "Primary")
            SpacerVertical(10.dp)
            ButtonPrimary(onClick = { /*TODO*/ }, text = "Primary")
            SpacerVertical(10.dp)

            ButtonPrimary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, iconRight = R.drawable.ic_info, enabled = false, text = "Primary")
            SpacerVertical(10.dp)
            ButtonPrimary(onClick = { /*TODO*/ }, iconRight = R.drawable.ic_info, enabled = false, text = "Primary")
            SpacerVertical(10.dp)
            ButtonPrimary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, enabled = false, text = "Primary")
            SpacerVertical(10.dp)
            ButtonPrimary(onClick = { /*TODO*/ }, enabled = false, text = "Primary")
            SpacerVertical(10.dp)

            ButtonSecondary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, iconRight = R.drawable.ic_info, text = "Secondary")
            SpacerVertical(10.dp)
            ButtonSecondary(onClick = { /*TODO*/ }, iconRight = R.drawable.ic_info, text = "Secondary")
            SpacerVertical(10.dp)
            ButtonSecondary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_info, text = "Secondary")
            SpacerVertical(10.dp)
            ButtonSecondary(onClick = { /*TODO*/ }, text = "Secondary")
            SpacerVertical(10.dp)

            ButtonSecondary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, iconRight = R.drawable.ic_info, enabled = false, text = "Secondary")
            SpacerVertical(10.dp)
            ButtonSecondary(onClick = { /*TODO*/ }, iconRight = R.drawable.ic_info, enabled = false, text = "Secondary")
            SpacerVertical(10.dp)
            ButtonSecondary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, enabled = false, text = "Secondary")
            SpacerVertical(10.dp)
            ButtonSecondary(onClick = { /*TODO*/ }, enabled = false, text = "Secondary")
            SpacerVertical(10.dp)

            ButtonTertiary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, iconRight = R.drawable.ic_info, text = "Secondary")
            SpacerVertical(10.dp)
            ButtonTertiary(onClick = { /*TODO*/ }, iconRight = R.drawable.ic_info, text = "Secondary")
            SpacerVertical(10.dp)
            ButtonTertiary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_info, text = "Secondary")
            SpacerVertical(10.dp)
            ButtonTertiary(onClick = { /*TODO*/ }, text = "Secondary")
            SpacerVertical(10.dp)

            ButtonTertiary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, iconRight = R.drawable.ic_info, enabled = false, text = "Secondary")
            SpacerVertical(10.dp)
            ButtonTertiary(onClick = { /*TODO*/ }, iconRight = R.drawable.ic_info, enabled = false, text = "Secondary")
            SpacerVertical(10.dp)
            ButtonTertiary(onClick = { /*TODO*/ }, iconLeft = R.drawable.ic_help, enabled = false, text = "Secondary")
            SpacerVertical(10.dp)
            ButtonTertiary(onClick = { /*TODO*/ }, enabled = false, text = "Secondary")
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