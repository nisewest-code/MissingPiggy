package com.mis.singp.iggy.utils.str

import android.content.Context
import com.appsflyer.AppsFlyerLib
import com.mis.singp.iggy.utils.pref.PrefMissingPiggy
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object StringSupport {

    const val emptyPage: String =
        "\"\\u003Chtml>\\u003Chead>\\u003C/head>\\u003Cbody>\\u003C/body>\\u003C/html>\""

    fun view(): String {
        return "android.intent.action.VIEW"
    }

    fun getIdFire(context: Context): String {
        return if (Linked.link.isEmpty()) {
            CoroutineScope(Dispatchers.Main).launch {
                PrefMissingPiggy.saveStatus(context, "organic")
            }
            Ids.organ()
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                PrefMissingPiggy.saveStatus(context,"non-organic")
            }
            Ids.noOrgan()
        }
    }

    fun getOrganic(
        context: Context,
        string: String,
        advertisingId: String,
        appslyerID: String
    ): String {
        val pixelid = PrefMissingPiggy.getPixelId(context)
        val fbclid = PrefMissingPiggy.getFbclid(context)
        return string
            .replace("{dp1}", "null")
            .replace("{dp2}", "null")
            .replace("{dp3}", "null")
            .replace("{dp4}", "null")
            .replace("{dp5}", "null")
            .replace("{dp6}", context.packageName)
            .replace("{dp7}", "razrab10")
            .replace("{client_id}", "null")
            .replace(
                "{appsflyer_id}",
                AppsFlyerLib.getInstance()
                    .getAppsFlyerUID(context).toString()
            )
            .replace("{advertising_id}", advertisingId)
            .replace("{offer_name}", "null")
            .replace("{appsdv}", appslyerID)
            .replace("{fbclid}", fbclid)
            .replace("{pixel}", pixelid)
    }

    fun getNonOrganic(
        context: Context,
        link: String,
        string: String,
        advertisingId: String,
        appslyerID: String
    ): String {
        val pixelid = PrefMissingPiggy.getPixelId(context)
        val fbclid = PrefMissingPiggy.getFbclid(context)
        val list = link.split("_").toMutableList()
        //
        //  ?sub_id_1={dp1}&sub_id_2={dp2}&sub_id_3={dp3}&sub_id_4={dp4}&appsflyer_id={appsflyer_id}&sub_id_6={dp6}&sub_id_7=razrab10&sub_id_8={dp8}&advertising_id={advertising_id}&offer_name={offer_name}&appsdv={appsdv}&fbclid={fbclid}&pixel={pixelid}
        if (list.size < 7) {
            list.add("empty5")
            list.add("empty6")
            list.add("empty7")
        }
        OneSignal.sendTag("offer", list[1])
        return string
            .replace("{dp1}", list[2])
            .replace("{dp2}", list[3])
            .replace("{dp3}", list[4])
            .replace("{dp4}", list[5])
            .replace("{dp5}", list[6])
            .replace("{dp6}", context.packageName)
            .replace("{dp7}", "razrab10")
            .replace("{client_id}", list[0])
            .replace(
                "{appsflyer_id}",
                AppsFlyerLib.getInstance()
                    .getAppsFlyerUID(context).toString()
            )
            .replace("{advertising_id}", advertisingId)
            .replace("{offer_name}", list[1])
            .replace("{appsdv}", appslyerID)
            .replace("{fbclid}", fbclid)
            .replace("{pixel}", pixelid)

    }
    
}