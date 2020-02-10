/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.api

import com.nikita.nullidea.db.UserEntity
import com.nikita.nullidea.model.BaseResponseModel
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {

    @POST("user/login")
    suspend fun signIn(@Body userRequestModel: UserRequestModel): BaseResponseModel<UserEntity>

    @POST("user/register")
    suspend fun signUp(@Body userRequestModel: UserRequestModel): BaseResponseModel<UserEntity>

}

data class UserRequestModel(
    val email: String,
    val password: String
)