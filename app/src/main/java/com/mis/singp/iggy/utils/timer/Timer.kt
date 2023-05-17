package com.mis.singp.iggy.utils.timer

import kotlinx.coroutines.*

class Timer(private  val  level: String, callbackTick: ()->Unit) {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private fun startCoroutineTimer(delayMillis: Long = 0, repeatMillis: Long = if (level == "easy") 1500 else 1000, action: () -> Unit) = scope.launch(
        Dispatchers.IO) {
        delay(delayMillis)
        if (repeatMillis > 0) {
            while (isActive) {
                action()
                delay(repeatMillis)
            }
        }
    }

    private val timer: Job = startCoroutineTimer() {
        scope.launch(Dispatchers.Main) {
            callbackTick()
        }
    }

    fun startTimer() {
        timer.start()
    }


    fun cancelTimer() {
        timer.cancel()
    }
}