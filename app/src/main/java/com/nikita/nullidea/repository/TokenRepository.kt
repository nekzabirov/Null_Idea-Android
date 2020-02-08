/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.repository

import com.nikita.nullidea.TAG
import com.nikita.nullidea.api.TokenApiService
import com.nikita.nullidea.model.TokenModel
import com.nikita.nullidea.unit.rest.AppRest
import com.nikita.nullidea.unit.tool.MyLog
import com.nikita.nullidea.unit.tool.PreferenceTools

class TokenRepository {

    suspend fun accessToken(): TokenModel {

        MyLog.d(TAG, "start load access token")

        val tokenModel = AppRest(
            "https://nullidea.eu.auth0.com/oauth/token",
            TokenApiService::class.java
        ).api().getToken()

        PreferenceTools.setAccessToken(tokenModel.accessToken)

        PreferenceTools.setAccessTokenDate(System.currentTimeMillis())

        PreferenceTools.setExpiresIn(tokenModel.expiresIn)

        PreferenceTools.setTokenType(tokenModel.tokenType)

        return tokenModel
    }

}