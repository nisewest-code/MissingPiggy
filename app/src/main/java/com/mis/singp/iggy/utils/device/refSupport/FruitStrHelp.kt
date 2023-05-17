package com.mis.singp.iggy.utils.device.refSupport

import android.content.Context
import com.mis.singp.iggy.utils.pref.PrefMissingPiggy
import com.mis.singp.iggy.utils.support.AmplUtil
import com.mis.singp.iggy.utils.support.Analytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object FruitStrHelp {

    fun getReferrerValue(context: Context, str: String) {
        var stringWithoutScheme: String = str
        Analytics.referrer(stringWithoutScheme)
        if (str.contains(Keys.FB_CLICK_ID.key) || str.contains(Keys.PIXEL_ID.key)) {
            if (str.take(str.length / 2).contains(':')) {
                stringWithoutScheme = str.removeScheme()
            }
            val (naming, query) = stringWithoutScheme.split(Delimiter.QUESTION.key)
            val map = query.split(Delimiter.AND.key).map { it.split(Delimiter.EQUALS.key) }
                .associate { it[0] to it[1] }
            Analytics.referrerCampaign(naming)
            CoroutineScope(Dispatchers.Main).launch {
                if (naming.isNotEmpty()) {
                    PrefMissingPiggy.saveCampaign(context, naming)
                }
                PrefMissingPiggy.saveFbclid(context, map[Keys.FB_CLICK_ID.key]?.removeBraces())
                Analytics.fbClickId(map[Keys.FB_CLICK_ID.key]?.removeBraces() ?: "")
                PrefMissingPiggy.savePixelId(context, map[Keys.PIXEL_ID.key]?.removeBraces())
                Analytics.pixelId(map[Keys.PIXEL_ID.key]?.removeBraces() ?: "")
                PrefMissingPiggy.saveStatus(context,"non-organic")
            }
//            StorageManager.campaign = naming

        }
    }
}

fun String.removeScheme(): String {
    return this.substring(this.lastIndexOf(':') + 3)
}

fun String.removeBraces(): String {
    return this.trim('{', '}')
}