/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.unit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikita.nullidea.TAG
import com.nikita.nullidea.unit.tool.MyLog

abstract class MyViewModel : ViewModel() {

    open val errorHandler = Threads.getErrorHandler(object : OnError {
        override fun onInternet() {
            MyLog.e(TAG, "onInternet")
            internetErrorLiveData.postValue(null)
        }
    })

    open val internetErrorLiveData = MutableLiveData<Any>()
}