package com.mis.singp.iggy.utils.support

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mis.singp.iggy.utils.pref.PrefMissingPiggy
import com.mis.singp.iggy.utils.str.Ids
import com.mis.singp.iggy.utils.str.Linked
import com.mis.singp.iggy.utils.str.StringSupport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object FireLib {
    fun init(context: Context, callbackSuccess: (link: String)->Unit, callbackFail: ()->Unit){
        val db = Firebase.firestore
        val docRef = db.collection("domain").document(StringSupport.getIdFire(context))
        docRef.get()
            .addOnSuccessListener { document ->
                Analytics.backendReceivedTime()
                // Если имя компании и диплинк не пришли
                val nameRef = "url"
                if (document != null) {
                    var urlFirebase = document.getString(nameRef)
                    if (urlFirebase != null) {
                        if (urlFirebase.isEmpty()) {
                            callbackFail()
                        } else {
                            Analytics.backendUrl(urlFirebase)
                            urlFirebase = if (Linked.link.isNotEmpty()) {
                                StringSupport.getNonOrganic(context, Linked.link, urlFirebase, Linked.advertisingId, Ids.appsId())
                            } else {
                                StringSupport.getOrganic(context, urlFirebase, Linked.advertisingId, Ids.appsId())

                            }
                            CoroutineScope(Dispatchers.Main).launch {
                                PrefMissingPiggy.saveStartUrl(context, urlFirebase)
                            }
                            Analytics.logFirstUrl(urlFirebase)
                            callbackSuccess(urlFirebase)
                        }
                    } else {
                        callbackFail()
                    }
                } else {
                    callbackFail()
                }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                callbackFail()
            }
    }
}