package com.mis.singp.iggy.utils.pref

import android.content.Context
import android.content.SharedPreferences
import com.mis.singp.iggy.utils.str.Pref
import com.mis.singp.iggy.utils.str.StringSupport
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class PrefMissingPiggy {

    companion object {
        private var mPreference: SharedPreferences? = null
        suspend fun initPref(context: Context) {
            coroutineScope {
                val param = async {
                    context.applicationContext
                        .getSharedPreferences(
                            Pref.getPrefKeyAppName(),
                            Context.MODE_PRIVATE
                        )
                }
                mPreference = param.await()
            }
        }

        fun getScore(context: Context): Int {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }

            return (mPreference?.getInt(Pref.getPrefKeyScore(), 0) ?: 0).toInt()
        }

        fun buy(context: Context){
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }

            val oldscore = getScore(context)
            val nScore = oldscore - 100
            mPreference!!.edit().putInt(Pref.getPrefKeyScore(), nScore).apply()
        }

        fun saveScore(context: Context, score: Int) {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            val oldscore = getScore(context)
            val nScore = oldscore + score

            saveBestScore(context, score)

            mPreference!!.edit().putInt(Pref.getPrefKeyScore(), nScore).apply()
        }

        fun getBestScore(context: Context): Int {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }

            return (mPreference?.getInt(Pref.getPrefKeyBestScore(), 0) ?: 0).toInt()
        }

        fun saveBestScore(context: Context, score: Int) {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            val best = getBestScore(context)
            if (score > best) {
                mPreference!!.edit().putInt(Pref.getPrefKeyBestScore(), score).apply()
            }
        }

        fun saveStartUrl(context: Context, url: String?) {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            mPreference!!.edit()
                .putString(Pref.getPrefKeyStartUrl(), url)
                .apply()
        }

        fun getStartUrl(context: Context): String {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            return mPreference?.getString(
                Pref.getPrefKeyStartUrl(),
                ""
            ) ?: ""
        }

        fun saveLevel(context: Context, level: String?) {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            mPreference!!.edit()
                .putString(Pref.getPrefKeyLevel(), level)
                .apply()
        }

        fun getLevel(context: Context): String {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            return mPreference?.getString(
                Pref.getPrefKeyLevel(),
                "easy"
            ) ?: "easy"
        }

        fun saveLastUrl(context: Context, url: String?) {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            mPreference!!.edit()
                .putString(Pref.getPrefKeyLastUrl(), url)
                .apply()
        }

        fun getLastUrl(context: Context): String {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            return mPreference?.getString(
                Pref.getPrefKeyLastUrl(),
                ""
            ) ?: ""
        }

        fun saveStatus(context: Context, url: String?) {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            mPreference!!.edit()
                .putString(Pref.getPrefKeyStatus(), url)
                .apply()
        }

        fun getStatus(context: Context): String {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            return mPreference!!.getString(
                Pref.getPrefKeyStatus(),
                ""
            ) ?: ""
        }

        fun saveCampaign(context: Context, url: String?) {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            mPreference!!.edit()
                .putString(Pref.getPrefKeyCampaign(), url)
                .apply()
        }

        fun getCampaign(context: Context): String {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            return mPreference!!.getString(
                Pref.getPrefKeyCampaign(),
                ""
            ) ?: ""
        }

        fun saveFbclid(context: Context, string: String?) {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            mPreference!!.edit()
                .putString(Pref.getPrefKeyFbclid(), string)
                .apply()
        }

        fun getFbclid(context: Context): String {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            return mPreference!!.getString(
                Pref.getPrefKeyFbclid(),
                "null"
            ) ?: "null"
        }

        fun savePixelId(context: Context, string: String?) {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            mPreference!!.edit()
                .putString(Pref.getPrefKeyPixelId(), string)
                .apply()
        }

        fun getPixelId(context: Context): String {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            return mPreference!!.getString(
                Pref.getPrefKeyPixelId(),
                "null"
            ) ?: "null"
        }

        fun saveDopLife(context: Context, s: Boolean) {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            mPreference!!.edit()
                .putBoolean(Pref.getPrefKeyAddLife(), s)
                .apply()
        }

        fun isDopLife(context: Context): Boolean {
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            return mPreference!!.getBoolean(
                Pref.getPrefKeyAddLife(),
                false
            ) ?: false
        }

        fun reset(context: Context){
            if (mPreference == null) {
                mPreference = context.applicationContext
                    .getSharedPreferences(
                        Pref.getPrefKeyAppName(),
                        Context.MODE_PRIVATE
                    )
            }
            mPreference!!.edit().clear().apply()
        }
    }

}