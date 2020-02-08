/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.unit.tool

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.nikita.nullidea.api.TokenApiService
import com.nikita.nullidea.model.TokenModel
import com.nikita.nullidea.repository.UserRepository
import com.nikita.nullidea.unit.rest.AppRest
import com.nikita.nullidea.unit.tool.PreferenceNames.ACCESS_TOKEN
import com.nikita.nullidea.unit.tool.PreferenceNames.ACCESS_TOKEN_DATE
import com.nikita.nullidea.unit.tool.PreferenceNames.EXPIRES_IN
import com.nikita.nullidea.unit.tool.PreferenceNames.FIREBASE_TOKEN
import com.nikita.nullidea.unit.tool.PreferenceNames.IS_USER_SIGN
import com.nikita.nullidea.unit.tool.PreferenceNames.TOKEN_TYPE
import com.nikita.nullidea.unit.tool.PreferenceNames.USER_ID

object PreferenceTools {

    private const val MY_PREFS_NAME = "Null_idea_preference"

    private var editor: SharedPreferences.Editor? = null
    private var store: SharedPreferences? = null

    @SuppressLint("CommitPrefEdits")
    fun init(context: Context) {
        editor = context.getSharedPreferences(
            MY_PREFS_NAME, MODE_PRIVATE).edit()
        store = context.getSharedPreferences(
            MY_PREFS_NAME, MODE_PRIVATE)
    }

    val isUserSign: Boolean by lazy {
        store
            ?.getBoolean(IS_USER_SIGN, false)!!
    }

    fun setUserSigned() {
        editor
            ?.putBoolean(IS_USER_SIGN, true)
            ?.apply()
    }

    fun setFirebaseToken(token: String) {
        editor
            ?.putString(FIREBASE_TOKEN, token)
            ?.apply()
    }

    suspend fun tokenFirebase(): String {

        if (store?.contains(FIREBASE_TOKEN)!!) {
            setFirebaseToken(
                UserRepository().getFirebaseToken()
            )
        }

        return store
            ?.getString(FIREBASE_TOKEN, null)!!
    }

    val userID: String by lazy {
        store?.getString(USER_ID, "non_user")!!
    }

    suspend fun accessToken(): TokenModel {

        if (
            !store?.contains(ACCESS_TOKEN)!!
            || !store?.contains(ACCESS_TOKEN_DATE)!!
            || !store?.contains(EXPIRES_IN)!!
            || !store?.contains(TOKEN_TYPE)!!
                ) {

            val tokenModel = AppRest(
                "https://nullidea.eu.auth0.com/oauth/token",
                TokenApiService::class.java
            ).api().getToken()

            setAccessToken(tokenModel.accessToken)

            setAccessTokenDate(System.currentTimeMillis())

            setExpiresIn(tokenModel.expiresIn)

            setTokenType(tokenModel.tokenType)

        }

        return TokenModel(
            store?.getString(ACCESS_TOKEN, "")!!,
            store?.getLong(EXPIRES_IN, 0)!!,
            store?.getString(TOKEN_TYPE, "")!!
        )
    }

    private fun setAccessToken(token: String) {
        editor?.putString(ACCESS_TOKEN, token)?.apply()
    }

    private fun setAccessTokenDate(date: Long) {
        editor?.putLong(ACCESS_TOKEN_DATE, date)?.apply()
    }

    private fun setExpiresIn(expires: Long) {
        editor?.putLong(EXPIRES_IN, expires)?.apply()
    }

    private fun setTokenType(tokenType: String) {
        editor?.putString(TOKEN_TYPE, tokenType)?.apply()
    }

}

object PreferenceNames {

    const val IS_USER_SIGN = "IS_USER_SIGN"

    const val FIREBASE_TOKEN = "FIREBASE_TOKEN"

    const val USER_ID = "USER_ID"

    const val ACCESS_TOKEN = "ACCESS_TOKEN"

    const val ACCESS_TOKEN_DATE = "ACCESS_TOKEN_DATE"

    const val EXPIRES_IN = "EXPIRES_IN"

    const val TOKEN_TYPE = "TOKEN_TYPE"

}