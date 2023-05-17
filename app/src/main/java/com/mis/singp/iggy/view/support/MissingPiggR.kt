package com.mis.singp.iggy.view.support

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mis.singp.iggy.ActivityMissingBoard
import com.mis.singp.iggy.utils.str.StringSupport
import com.mis.singp.iggy.utils.support.Analytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MissingPiggR(private val activity: MutableList<Activity?>, private val uri:String) {
    fun vozvratR(): List<ArrayList<MutableList<WebViewClient>>> =
        listOf(arrayListOf(mutableListOf(Comm())))


    fun vozrtStr(): List<String?> = listOf(StringSupport.view())

    inner class Comm : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String?) {
            super.onPageFinished(view, url)
            url?.let {
                Analytics.openUrl(it)
            }
            if (url == uri){
                Analytics.firstUrlOpenTime()
            }
            view.evaluateJavascript(    "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();"
            ) { html: String ->    if (html == StringSupport.emptyPage) {
                val intent = Intent(activity[0], ActivityMissingBoard::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                activity[0]?.startActivity(intent)    }
            }
//            CoroutineScope(Dispatchers.IO).launch {
//                PrefUtil.saveLastUrl(url)
//            }
        }
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

            if (url.startsWith("https://m.facebook.com/oauth/error")) {
                return true
            }
            return try {
                if (URLUtil.isNetworkUrl(url)) {
                    return false
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        withContext(Dispatchers.Main) {
                            val intent = Intent(vozrtStr()[0])
                            intent.data = Uri.parse(url)
                            activity[0]?.startActivity(intent)
                        }
                    }
                }
                true
            } catch (unused: java.lang.Exception) {
                false
            }
        }

        override fun onReceivedError(
            view: WebView?,
            errorCode: Int,
            description: String?,
            failingUrl: String?
        ) {
            if (errorCode == 404){
                val intent = Intent(activity[0], ActivityMissingBoard::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                activity[0]?.startActivity(intent)
            } else {
                super.onReceivedError(view, errorCode, description, failingUrl)
            }
        }
    }
}