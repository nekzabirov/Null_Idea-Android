/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.api

import com.nikita.nullidea.model.AuthModel
import com.nikita.nullidea.model.TokenModel
import retrofit2.http.POST

interface TokenApiService {
    @POST("")
    suspend fun getToken(authModel: AuthModel = AuthModel()): TokenModel
}