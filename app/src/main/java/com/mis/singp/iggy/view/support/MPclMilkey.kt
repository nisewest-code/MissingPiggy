package com.mis.singp.iggy.view.support

import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView

class MPclMilkey {
    fun oreoR(webView: () -> WebView) {
        webView().layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        CookieManager.getInstance().setAcceptThirdPartyCookies( webView(), true)
        CookieManager.getInstance().setAcceptCookie(true)
        webView().setOnTouchListener { _, _ ->
            CookieManager.getInstance().flush()
            false
        }
    }
}