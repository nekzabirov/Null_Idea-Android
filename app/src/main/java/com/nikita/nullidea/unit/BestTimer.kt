/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.unit

import kotlinx.coroutines.*

class BestTimer(private val timeSecond: Int) {

    var onTimeDone: () -> Unit = {}

    var onTime: (Int) -> Unit = {}

    private var timeJob: Job? = null

    fun start() {
        timeJob = GlobalScope.launch(Threads.ioDispatcher) {
            for (time in 1..timeSecond) {
                delay(1000)
                withContext(Threads.mainDispatcher) {
                    onTime.invoke(timeSecond - time)
                }
            }
            withContext(Threads.ioDispatcher) {
                onTimeDone.invoke()
            }
        }
    }

    fun stop() {
        timeJob?.cancel()
    }

}