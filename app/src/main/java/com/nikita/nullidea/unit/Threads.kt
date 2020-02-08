/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.unit

import com.nikita.nullidea.TAG
import com.nikita.nullidea.unit.tool.MyLog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object Threads {

    private val errorHandler = CoroutineExceptionHandler { _, error ->
        MyLog.e(TAG, "error on dispatchers: ${error.localizedMessage}")
    }

    val ioDispatcher = Dispatchers.IO + errorHandler

    val mainDispatcher = Dispatchers.Main + errorHandler

    fun getErrorHandler(onError: OnError): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, error ->

            if (   error is UnknownHostException
                || error is SocketTimeoutException
                || error is ConnectException)
            {
                MyLog.e(TAG, "on internet error: ${error.localizedMessage}")
                onError.onInternet()
            } else {
                MyLog.e(TAG, "on unresolved error: ${error.localizedMessage}")
            }

        }
    }

}

interface OnError {
    fun onInternet()
}