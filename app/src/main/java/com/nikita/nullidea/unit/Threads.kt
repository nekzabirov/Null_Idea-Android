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

object Threads {

    private val errorHandler = CoroutineExceptionHandler { _, error ->
        MyLog.e(TAG, "error on dispatchers: ${error.localizedMessage}")
    }

    val ioDispatcher = Dispatchers.IO + errorHandler

    val mainDispatcher = Dispatchers.Main + errorHandler


}