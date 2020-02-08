/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.repository

import com.google.firebase.iid.FirebaseInstanceId
import com.nikita.nullidea.TAG
import com.nikita.nullidea.api.TokenApiService
import com.nikita.nullidea.model.TokenModel
import com.nikita.nullidea.unit.rest.AppRest
import com.nikita.nullidea.unit.tool.MyLog
import com.nikita.nullidea.unit.tool.PreferenceTools
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import com.google.android.gms.tasks.OnCompleteListener

class TokenRepository {

    suspend fun getFirebaseToken(): String = suspendCoroutine { continuation ->
        FirebaseInstanceId.getInstance()
            .instanceId
            .addOnCompleteListener(
                OnCompleteListener { task ->

                    if (!task.isSuccessful) {
                        continuation.resumeWithException(
                            IllegalArgumentException("Cannot load firebase token")
                        )
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    if (token == null) {
                        continuation.resumeWithException(
                            IllegalArgumentException("Firebase token is null")
                        )
                        return@OnCompleteListener
                    }

                    continuation.resume(token)
                }
            )
    }

    suspend fun accessToken(): TokenModel {

        MyLog.d(TAG, "start load access token")

        val tokenModel = AppRest(
            "https://nullidea.eu.auth0.com/oauth/",
            TokenApiService::class.java
        ).api().getToken()

        PreferenceTools.setAccessToken(tokenModel.accessToken)

        PreferenceTools.setAccessTokenDate(System.currentTimeMillis())

        PreferenceTools.setExpiresIn(tokenModel.expiresIn)

        PreferenceTools.setTokenType(tokenModel.tokenType)

        return tokenModel
    }

}