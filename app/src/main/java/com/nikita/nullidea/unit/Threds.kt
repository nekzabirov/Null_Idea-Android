/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.unit

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

object Threds {

    private val errorHandler = CoroutineExceptionHandler { _, _ ->

    }

    private val ioDispatcher = Dispatchers.IO + errorHandler

}