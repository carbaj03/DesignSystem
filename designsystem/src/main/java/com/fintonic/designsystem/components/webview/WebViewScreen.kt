package com.fintonic.designsystem.components.webview

import android.content.Intent
import android.net.Uri
import android.webkit.ValueCallback
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.fintonic.designsystem.components.Loader
import com.fintonic.designsystem.components.Screen
import com.fintonic.designsystem.components.bottomsheet.BottomSheet
import com.fintonic.designsystem.components.bottomsheet.BottomSheetModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WebViewScreen(
    title: String,
    onBack: () -> Unit,
    url: String,
    navigator: Navigator,
    options: Options? = null,
    menuItems: List<MenuItem>? = null,
    captureBackPresses: Boolean = true,
    onFileChooser: (filePathCallback: ValueCallback<Array<Uri>>?, BottomSheetModel) -> Unit = { _, _ -> },
) {
    val scaffoldState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val state: WebViewState = rememberWebViewState(url = url,)

    var newTitle by remember(title) { mutableStateOf(title) }
    var toolbarItems: List<MenuItem>? by remember { mutableStateOf(menuItems) }
    var isLoading: Boolean by remember { mutableStateOf(true) }
    var callback: ValueCallback<Array<Uri>>? by remember { mutableStateOf(null) }

    ModalBottomSheetLayout(
        sheetState = scaffoldState,
        sheetContent = {
            options?.let {
                BottomSheet(
                    onClick = { action ->
                        callback?.let { onFileChooser(it, action) }
                        scope.launch { scaffoldState.hide() }
                    },
                    title = stringResource(id = options.title),
                    values = options.actions
                )
            } ?: Text(text = "")
        }
    ) {
        Screen(
            title = newTitle,
            onClose = onBack,
            itemRight = {
                toolbarItems?.forEach {
                    IconButton(onClick = it.action) {
                        Icon(painter = painterResource(id = it.icon), contentDescription = null)
                    }
                }
            }
        ) {
            WebView(
                state = state,
                onCreated = {
                    scope.launch {
                        snapshotFlow { state.isLoading }.distinctUntilChanged().collect {
                            isLoading = it
                        }
                    }
                    scope.launch {
                        snapshotFlow { state.content.getCurrentUrl() }.distinctUntilChanged().collect {
                            WebViewAction(it)?.run {
                                when (this) {
                                    is WebViewAction.OpenBrowser -> {
                                        state.content = WebContent.Url(this.url)
                                    }
                                    is WebViewAction.Exit -> {
                                        navigator.exit()
                                    }
                                    is WebViewAction.WhatsUp -> {
                                        navigator.fromUri(uri, Intent.ACTION_VIEW)
                                    }
                                    is WebViewAction.Mail -> {
                                        navigator.fromUri(uri, Intent.ACTION_VIEW)
                                    }
                                    is WebViewAction.Pdf -> {
                                        navigator.fromUri(uri, Intent.ACTION_VIEW)
                                    }
                                    is WebViewAction.Telephone -> {
                                        navigator.fromUri(uri, Intent.ACTION_DIAL)
                                    }
                                    is WebViewAction.CustomTitle -> {
                                        newTitle = value
                                    }
                                    is WebViewAction.HideContact -> {
                                        toolbarItems = emptyList()
                                    }
                                    is WebViewAction.ShowContact -> {
                                        toolbarItems = menuItems
                                    }
                                }
                            }
                        }
                    }

                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.allowFileAccess = true
                },
                onFileChooser = {
                    callback = it
                    scope.launch { scaffoldState.show() }
                    true
                },
                captureBackPresses = captureBackPresses,
                onError = { _, _ -> }
            )
        }
    }
    if (isLoading) {
        Loader()
    }
}