package com.mis.singp.iggy


import android.app.Application
import com.mis.singp.iggy.utils.device.DeviceUtil
import com.mis.singp.iggy.utils.pref.PrefMissingPiggy
import com.mis.singp.iggy.utils.support.Analytics
import com.mis.singp.iggy.utils.support.OneLib
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppMissingPiggy : Application() {

    // два документа firebase
    // webview внедряю в контейнер
    // переписать стек вызовов
    // 2 - 3 прилы меняем заглушку
    // Aqurus
 
    // интернет
    // Aceses faid
    // Gaid
    // Application extends



    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.Default).launch {
            val a = PrefMissingPiggy.initPref(this@AppMissingPiggy)
        }
        Analytics.init(this)

//        Bugsee.launch(this, Ids.bugId())

        DeviceUtil.checkDevice(
            this, callbackFail = {},
            callbackSuccess = {
                OneLib.init(this)
            }
        )

        // promptForPushNotifications will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
    }
}