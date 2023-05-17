package com.mis.singp.iggy.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.mis.singp.iggy.ActivityMissingBoard
import com.mis.singp.iggy.databinding.ActivityLauchMissingPiggyBinding
import com.mis.singp.iggy.utils.device.DeviceUtil
import com.mis.singp.iggy.utils.device.Referer
import com.mis.singp.iggy.utils.pref.PrefMissingPiggy
import com.mis.singp.iggy.utils.str.Linked
import com.mis.singp.iggy.utils.support.*
import com.mis.singp.iggy.utils.timer.TimerLan
import com.mis.singp.iggy.view.support.WbCustom
import com.onesignal.OneSignal
import kotlinx.coroutines.*

class ActivityLauchMissingPiggy : AppCompatActivity() {
    private var wbCustom: WbCustom? = null
    private lateinit var binding: ActivityLauchMissingPiggyBinding
    private var timer: TimerLan? = null
    private val listener =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { intent ->
                    wbCustom?.listenerSuccess(intent)
                }
            } else {
                wbCustom?.listenerOnFail()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauchMissingPiggyBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        supportActionBar?.hide()

        Analytics.geo()

        DeviceUtil.checkDevice(
            this, callbackFail = {
                Analytics.devMode()
                startActivity()
//                callbackSuccess()
            },
            callbackSuccess = this::callbackSuccess
        )
    }

    // Если прошел по условию
    private fun callbackSuccess() {
        Referer.init(this)

        Analytics.appOpen()

        CoroutineScope(Dispatchers.Main).launch {
            val adId =
                withContext(Dispatchers.Default) { getAdvertingId() }
            Linked.advertisingId = adId
            OneLib.setExternalUserId(adId)
            val firstUrl = PrefMissingPiggy.getStartUrl(this@ActivityLauchMissingPiggy)
            if (firstUrl.isEmpty()) {
                // Инициализируем Appsflyer
                startAppInit()
                timer = TimerLan {
                    generateLink()
                }
                timer?.startTimer()
            } else {
                Analytics.repearEnter()
                Linked.link = firstUrl
                generateLink()
            }
        }
    }

    private fun callback(link: String) {
        if (link.isNotEmpty()) {
            Linked.link = link
            timer?.cancelTimer()
        }
    }

    private suspend fun getAdvertingId(): String {
        return AdvertisingIdClient.getAdvertisingIdInfo(applicationContext).id.toString()
    }

    private fun startAppInit() {
        AppsLib.init(this, this::callback)
        FbLib.init(this, this::callback)
    }

    private fun generateLink() {
        //Настраиваем конфиг FirebaseRemote
        FireLib.init(this, callbackSuccess = {
            wbCustom = WbCustom(this, binding, listener)
            wbCustom?.init()
        }, callbackFail = {
            startActivity()
        })
    }

    private fun startActivity() {
        val intent = Intent(this, ActivityMissingBoard::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            wbCustom?.onWindowFocusChanged(window)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        wbCustom?.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        wbCustom?.onBackPressed {
            super.onBackPressed()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        wbCustom?.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        wbCustom?.onResume()
    }

    override fun onPause() {
        super.onPause()
        wbCustom?.onPause()
    }
}