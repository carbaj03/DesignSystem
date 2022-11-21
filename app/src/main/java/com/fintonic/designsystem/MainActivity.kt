package com.fintonic.designsystem

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fintonic.designsystem.components.*
import com.fintonic.designsystem.components.bottomsheet.BottomSheet
import com.fintonic.designsystem.components.bottomsheet.BottomSheetModel
import com.fintonic.designsystem.components.button.ButtonIcon
import com.fintonic.designsystem.components.button.ButtonPrimary
import com.fintonic.designsystem.components.button.ButtonSecondary
import com.fintonic.designsystem.components.button.ButtonTertiary
import com.fintonic.designsystem.components.input.InputCurrency
import com.fintonic.designsystem.components.input.InputText
import com.fintonic.designsystem.components.input.SubText
import com.fintonic.designsystem.components.search.Searcher
import com.fintonic.designsystem.components.text.BoldSpanned
import com.fintonic.designsystem.components.text.Text
import com.fintonic.designsystem.components.text.TextSpanned
import com.fintonic.designsystem.components.text.TextSpannedStyle
import com.fintonic.designsystem.components.webview.MenuItem
import com.fintonic.designsystem.components.webview.Navigator
import com.fintonic.designsystem.components.webview.Options
import com.fintonic.designsystem.components.webview.WebViewScreen
import com.fintonic.designsystem.foundation.*
import kotlinx.coroutines.delay
import java.io.File

class MainActivity : ComponentActivity() {

    var uploadMessage: ValueCallback<Array<Uri>>? = null

    val photoResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getStringExtra("RESULT")
                ?.let { File(it).toUri() }
                ?.let { uploadMessage?.onReceiveValue(arrayOf(it)) }
                ?: uploadMessage?.onReceiveValue(null)
            uploadMessage = null
        } else {
            uploadMessage?.onReceiveValue(null)
            uploadMessage = null
        }
    }

    val fileChooserResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            WebChromeClient.FileChooserParams.parseResult(result.resultCode, result.data)
                ?.let { uploadMessage?.onReceiveValue(it) }
            uploadMessage = null
        } else {
            uploadMessage?.onReceiveValue(null)
            uploadMessage = null
        }
    }

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

                    LaunchedEffect(key1 = null, block = { delay(2000); state = SnackBarState.Show("asfd", AppColor.Green); delay(2000); state = null })
                    LaunchedEffect(key1 = null, block = { isLoading = true; delay(2000); isLoading = false })

                    ButtonScreen(onBack = { navController.popBackStack() }, state = state, isLoading = isLoading)
                }
                composable("input") {
                    InputScreen(onBack = { navController.popBackStack() })
                }
                composable("webview") {
                    WebViewScreen(onBack = { navController.popBackStack() })
                }
            }
        }
    }


    @Composable
    fun WebViewScreen(onBack: () -> Unit) {
        WebViewScreen(
            title = "WebView",
            onBack = onBack,
            navigator = object : Navigator {
                override fun fromUri(uri: Uri, actionView: String) {
                    TODO("Not yet implemented")
                }

                override fun exit() {
                    TODO("Not yet implemented")
                }
            },
            url = "https://sign-app.sandbox.signaturit.com/en/34d1213/d9132a44-5497-4543-ab23-1a2cc80d7349/1f0e41e1-e891-4bec-b995-90b726a34895/signers/0/documents/1f0e41e1-e891-4bec-b995-90b726a34895",
            menuItems = listOf(MenuItem(action = {}, R.drawable.ic_help), MenuItem(action = {}, R.drawable.ic_info)),
            options = Options(R.string.app_name, MoreActionExchange.values().toList()),
            onFileChooser = { filePathCallback, action ->
                if (uploadMessage != null) {
                    uploadMessage?.onReceiveValue(null)
                    uploadMessage = null
                }

                uploadMessage = filePathCallback

                when (action) {
                    MoreActionExchange.Photo -> {
//                        photoResult.launch(InsurancePolicyCaptureActivity(this))
                    }
                    MoreActionExchange.File -> {
                        val contentSelection = Intent(Intent.ACTION_GET_CONTENT)
                        contentSelection.addCategory(Intent.CATEGORY_OPENABLE)
                        contentSelection.type = "*/*"
                        val extraMimeTypes = arrayOf("application/pdf", "image/jpeg", "image/png")
                        contentSelection.putExtra(Intent.EXTRA_MIME_TYPES, extraMimeTypes)
                        fileChooserResult.launch(Intent.createChooser(contentSelection, getString(action.text)))
                    }
                }
            }

        )
    }
}

enum class MoreActionExchange(
    @StringRes override val text: Int
) : BottomSheetModel {
    Photo(androidx.compose.ui.R.string.close_drawer),
    File(androidx.compose.ui.R.string.close_sheet),
}

@SuppressLint("ComposableNaming")
@Composable
fun <A> LaunchEffect(initial: A, action: suspend () -> A): A {
    var colors: A by remember { mutableStateOf(initial) }
    LaunchedEffect(null) { colors = action() }
    return colors
}

@OptIn(ExperimentalMaterialApi::class)
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
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(bottom = it.calculateBottomPadding())
                .background(AppTheme.colors.background.color),
            columns = GridCells.Fixed(2),
            contentPadding = it
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
            item {
                MyCard(onClick = { navController.navigate("webview") }) {
                    Text(text = "Webview", style = appTypography.bodyM, color = AppColor.Black)
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InputScreen(onBack: (() -> Unit)) {
    Screen(
        title = "Buttons",
        onBack = onBack
    ) {
        var text by remember { mutableStateOf("") }
        var text1 by remember { mutableStateOf("") }
        var text2 by remember { mutableStateOf("") }
        var text3 by remember { mutableStateOf("") }

        LaunchedEffect(key1 = "Initial", block = {
            text2 = "324"
            text3 = "1"
        })

        Column(Modifier.padding(it)) {
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
            SpacerVertical(height = 20.dp)
            InputCurrency(
                text = text2,
                onTextChange = { text2 = it },
                placeholder = "sadfs",
                currency = "€",
                subText = SubText.Info("Tendras : 222")
            )
            Row() {
                Searcher(text = text3, placeholder = "Busca", onTextChange = { text3 = it }, )
                Spacer(8.dp)
                ButtonTertiary(text = "sadfds", onClick = { /*TODO*/ })
            }
//            InputCurrency(
//                text = text3,
//                onTextChange = { text3 = it },
//                placeholder = "sadfs",
//                currency = "€",
//                subText = SubText.Info("Tendras : 222"),
//                allowDecimals = true
//            )
            BottomSheet(title = "sf") {

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ButtonScreen(onBack: (() -> Unit), state: SnackBarState?, isLoading: Boolean) {
    val scrollState = rememberScrollState()
    Screen(
        title = "Buttons",
        onBack = onBack,
        snackBarState = state,
        isLoading = isLoading,
        scrollState = scrollState
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(scrollState)
        ) {
            ButtonIcon(icon = R.drawable.ic_help, onClick = { /*TODO*/ }, color = AppColor.Black, colorIcon = AppColor.Blue)

            ButtonPrimary(onClick = { /*TODO*/ }, text = "Primary", modifier = Modifier.fillMaxWidth())
            SpacerVertical(10.dp)
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TypographyScreen(
    typographys: List<Pair<String, TextStyle>>,
    onBack: (() -> Unit)
) {
    val context = LocalContext.current
    Screen(
        title = "Buttons",
        onBack = onBack
    ) {

        LazyColumn(content = {
            item {
                BoldSpanned(style = appTypography.detail, text = "Alejandro Carbajo Vidales", textInBold = arrayOf("Hola", "Alejandro", "Vidales"))

                TextSpanned(
                    TextSpannedStyle("Hola", appTypography.bodyL, AppColor.Coral.color),
                    TextSpannedStyle(
                        text = "Alejandro Carbajo",
                        textStyle = appTypography.bodyS,
                        color = AppColor.Blue30.color,
                        onClick = { Toast.makeText(context, it, Toast.LENGTH_LONG).show() },
                    ),
                    style = appTypography.detail.copy(color = AppColor.Orange70.color),
                    text = "Hola Alejandro Carbajo Vidales",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            items(typographys) {
                Text(
                    style = it.second,
                    text = it.first,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
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
    DesignSystemTheme(false) {
        Greeting("Android")
    }
}