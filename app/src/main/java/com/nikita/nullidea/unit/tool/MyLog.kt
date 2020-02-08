/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.unit.tool

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.perf.FirebasePerformance
import com.nikita.nullidea.unit.tool.EventKeys.ERROR
import com.nikita.nullidea.unit.tool.EventKeys.MSG
import com.nikita.nullidea.unit.tool.EventKeys.TAG
import com.nikita.nullidea.unit.tool.EventKeys.USER_LOG

object MyLog {

    fun init(context: Context) {
        this.context = context
    }

    private lateinit var context: Context

    private val firebasePerformance = FirebasePerformance.getInstance()

    private val firebaseAnalytics: FirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(context).apply {
            setUserId(PreferenceTools.userID)
        }
    }

    fun d(tag: String, msg: String) {
        event(tag, msg)
        Log.d(tag, msg)
    }

    fun e(tag: String, msg: String, error: Throwable? = null) {
        if (error != null)
            Log.e(tag, msg, error)
        else
            Log.e(tag, msg)

        event(tag, msg, error)
    }

    private fun event(tag: String, mgs: String) {
        val params = Bundle().apply {
            putString(TAG, tag)
            putString(MSG, mgs)
        }
        firebaseAnalytics.logEvent(USER_LOG, params)
    }

    private fun event(tag: String, mgs: String, error: Throwable?) {
        val params = Bundle().apply {
            putString(TAG, tag)
            putString(MSG, "$mgs ${error?.localizedMessage}")
            putBoolean(ERROR, true)
        }
        firebaseAnalytics.logEvent(USER_LOG, params)
    }

}

private object EventKeys {

    const val TAG = "TAG"
    const val MSG = "MSG"
    const val USER_LOG = "USER_LOG"
    const val ERROR = "ERROR"

}