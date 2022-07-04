package com.fintonic.designsystem.components.webview

import android.net.Uri
import java.net.URLDecoder

sealed interface WebViewAction {
    companion object {
        operator fun invoke(url: String?): WebViewAction? {
            if (url == null) return null
            return try {
                when {
                    url.openBrowser() -> OpenBrowser(url.substringAfter("url="))
                    url.customTitle() -> url.getCustomTitle()?.let { CustomTitle(it) }
                    url.showHelp() -> ShowContact
                    url.hideHelp() -> HideContact
                    url.isPdfUrl() -> Pdf(Uri.parse(url))
                    url.isWhatsAppUrl() -> WhatsUp(Uri.parse(url))
                    url.isTelephoneUrl() -> Telephone(Uri.parse(url))
                    url.isMailUrl() -> Mail(Uri.parse(url))
                    url.exit() -> Exit
                    else -> null
                }
            } catch (ex: java.lang.Exception) {
                null
            }
        }

        private fun String.isPdfUrl(): Boolean =
            endsWith(".pdf")

        private fun String.isWhatsAppUrl(): Boolean =
            startsWith("whatsapp:")

        private fun String.isTelephoneUrl(): Boolean =
            startsWith("tel:")

        private fun String.isMailUrl(): Boolean =
            contains("mailto:")

        private fun String.customTitle(): Boolean =
            contains("funnel_name=")

        private fun String.getCustomTitle(): String? =
            try {
                val titleSplit = split("funnel_name=")
                val title = if (titleSplit[1].contains("&")) {
                    titleSplit[1].substring(0, titleSplit[1].indexOf("&"))
                } else {
                    titleSplit[1]
                }
                URLDecoder.decode(title, "UTF-8")
            } catch (e: Exception) {
                null
            }

        private fun String.showHelp(): Boolean =
            contains("funnel_help=true")

        private fun String.hideHelp(): Boolean =
            contains("funnel_help=false")

        private fun String.exit(): Boolean =
            contains("fintonic://exit")

        private fun String.exitDashboard(): Boolean =
            contains("fintonic://dashboard")

        private fun String.openBrowser(): Boolean =
            contains("fintonic://openbrowser")
                    || contains("destination=openbrowser")
    }

    object Exit : WebViewAction

    @JvmInline
    value class Pdf(val uri: Uri) : WebViewAction

    @JvmInline
    value class Telephone(val uri: Uri) : WebViewAction

    @JvmInline
    value class Mail(val uri: Uri) : WebViewAction

    @JvmInline
    value class WhatsUp(val uri: Uri) : WebViewAction

    @JvmInline
    value class OpenBrowser(val url: String) : WebViewAction

    @JvmInline
    value class CustomTitle(val value: String) : WebViewAction

    object ShowContact : WebViewAction

    object HideContact : WebViewAction
}