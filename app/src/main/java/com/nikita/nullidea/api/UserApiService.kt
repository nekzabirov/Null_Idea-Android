/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.api

import com.nikita.nullidea.db.UserEntity
import com.nikita.nullidea.model.BaseResponseModel
import retrofit2.http.Body
import retrofit2.http.GET

interface UserApiService {

    @GET("user")
    suspend fun signIn(@Body userRequestModel: UserRequestModel): BaseResponseModel<UserEntity>

}

data class UserRequestModel(
    val email: String,
    val password: String
)