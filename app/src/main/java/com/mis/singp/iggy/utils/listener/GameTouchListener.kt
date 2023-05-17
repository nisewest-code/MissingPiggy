package com.mis.singp.iggy.utils.listener

import android.os.SystemClock
import android.view.MotionEvent
import android.view.View

class GameTouchListener(private val callback:()->Unit): View.OnTouchListener {
    private val REPEAT_INTERVAL = 0L // интервал повтора в миллисекундах

    private var lastAction = 0L

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val currTime = SystemClock.uptimeMillis()
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastAction = currTime
                method()
            }
            MotionEvent.ACTION_MOVE -> if (currTime - lastAction >= REPEAT_INTERVAL) {
                lastAction = currTime
                method()
            }
        }
        return true
    }

    private fun method() {
        callback()
    }

}