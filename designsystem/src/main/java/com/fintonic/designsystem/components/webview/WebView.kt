package com.fintonic.designsystem.components.webview

import android.annotation.TargetApi
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebView(
    state: WebViewState,
    modifier: Modifier = Modifier,
    captureBackPresses: Boolean = true,
    navigator: WebViewNavigator = rememberWebViewNavigator(),
    onCreated: WebView.() -> Unit = {},
    onFileChooser: (filePathCallback: ValueCallback<Array<Uri>>?) -> Boolean = { false },
    onError: (request: WebResourceRequest?, error: WebResourceError?) -> Unit = { _, _ -> }
) {
    var webView by remember { mutableStateOf<WebView?>(null) }

    BackHandler(captureBackPresses && navigator.canGoBack) {
        webView?.goBack()
    }

    LaunchedEffect(webView, navigator) {
        with(navigator) { webView?.handleNavigationEvents() }
    }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                onCreated(this)

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                webChromeClient = object : WebChromeClient() {
                    override fun onReceivedTitle(view: WebView?, title: String?) {
                        super.onReceivedTitle(view, title)
                        state.pageTitle = title
                    }

                    override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
                        super.onReceivedIcon(view, icon)
                        state.pageIcon = icon
                    }

                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        if (state.loadingState is LoadingState.Finished) return
                        state.loadingState = LoadingState.Loading(newProgress / 100.0f)
                    }

                    override fun onShowFileChooser(
                        webView: WebView?,
                        filePathCallback: ValueCallback<Array<Uri>>?,
                        fileChooserParams: FileChooserParams?
                    ): Boolean = onFileChooser(filePathCallback)
                }

                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        state.loadingState = LoadingState.Loading(0.0f)
                        state.errorsForCurrentRequest.clear()
                        state.pageTitle = null
                        state.pageIcon = null
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        state.loadingState = LoadingState.Finished
                        navigator.canGoBack = view?.canGoBack() ?: false
                        navigator.canGoForward = view?.canGoForward() ?: false
                    }

                    override fun doUpdateVisitedHistory(
                        view: WebView?,
                        url: String?,
                        isReload: Boolean
                    ) {
                        super.doUpdateVisitedHistory(view, url, isReload)
                        // WebView will often update the current url itself.
                        // This happens in situations like redirects and navigating through
                        // history. We capture this change and update our state holder url.
                        // On older APIs (28 and lower), this method is called when loading
                        // html data. We don't want to update the state in this case as that will
                        // overwrite the html being loaded.
                        if (url != null &&
                            !url.startsWith("data:text/html") &&
                            state.content.getCurrentUrl() != url
                        ) {
                            state.content = WebContent.Url(url)
                        }
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)

                        if (error != null) {
                            state.errorsForCurrentRequest.add(WebViewError(request, error))
                        }

                        onError(request, error)
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        // Override all url loads to make the single source of truth
                        // of the URL the state holder Url
                        request?.let {
                            state.content = WebContent.Url(it.url.toString())
                        }
                        return true
                    }
                }
            }.also { webView = it }
        },
        modifier = modifier
    ) { view ->
        when (val content = state.content) {
            is WebContent.Url -> {
                val url = content.url

                if (url.isNotEmpty() && url != view.url) {
                    view.loadUrl(url)
                }
            }
            is WebContent.Data -> {
                view.loadDataWithBaseURL(content.baseUrl, content.data, null, "utf-8", null)
            }
        }

        navigator.canGoBack = view.canGoBack()
        navigator.canGoForward = view.canGoForward()
    }
}


/**
 * Creates a WebView state that is remembered across Compositions.
 *
 * @param url The url to load in the WebView
 */
@Composable
fun rememberWebViewState(url: String) =
    remember(url) { WebViewState(WebContent.Url(url)) }