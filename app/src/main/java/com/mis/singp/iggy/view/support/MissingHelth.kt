package com.mis.singp.iggy.view.support

import android.webkit.WebSettings
import android.webkit.WebView

class MissingHelth {
    fun tedt(webView: () -> WebView?, answer: () -> Boolean) {
        val webViewIn: WebView? = webView()
        val answerIn: Boolean = answer()
        webViewIn?.isSaveEnabled = answerIn
        webViewIn?.isFocusableInTouchMode = answerIn
        webViewIn?.isFocusable = answerIn
        try {
            webViewIn?.settings?.setRenderPriority(WebSettings.RenderPriority.HIGH)
            webViewIn?.settings?.allowContentAccess = answerIn
            webViewIn?.settings?.mediaPlaybackRequiresUserGesture = false
            webViewIn?.settings?.setSupportMultipleWindows(answerIn)
            webViewIn?.settings?.pluginState = WebSettings.PluginState.ON
            webViewIn?.settings?.cacheMode = WebSettings.LOAD_DEFAULT
            webViewIn?.settings?.loadsImagesAutomatically = answerIn
            webViewIn?.settings?.mixedContentMode = 0
            webViewIn?.settings?.saveFormData = answerIn
            webViewIn?.settings?.allowFileAccess = answerIn
            webViewIn?.settings?.javaScriptEnabled = answerIn
            webViewIn?.settings?.setEnableSmoothTransition(answerIn)
            webViewIn?.settings?.databaseEnabled = answerIn
            webViewIn?.settings?.savePassword = answerIn
            webViewIn?.settings?.domStorageEnabled = answerIn
            webViewIn?.settings?.allowUniversalAccessFromFileURLs = answerIn
            webViewIn?.settings?.javaScriptCanOpenWindowsAutomatically = answerIn
            webViewIn?.settings?.allowFileAccessFromFileURLs = answerIn


            webViewIn?.settings?.setSupportZoom(true)
            webViewIn?.settings?.builtInZoomControls = true
            webViewIn?.settings?.displayZoomControls = false

            webViewIn?.settings?.useWideViewPort = true

            webViewIn?.settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            webViewIn?.settings?.setPluginState(WebSettings.PluginState.ON)
            webViewIn?.settings?.apply {
                userAgentString = userAgentString.replace("; wv", "")
            }
        } catch (e: Exception) {
            e.stackTrace
        }


    }
}