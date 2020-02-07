package com.nikita.nullidea.unit

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object PreferenceTools {

    private const val MY_PREFS_NAME = "Null_idea_preference"

    private var editor: SharedPreferences.Editor? = null
    private var store: SharedPreferences? = null

    @SuppressLint("CommitPrefEdits")
    fun init(context: Context) {
        editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit()
        store = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE)
    }


}