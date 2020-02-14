/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.api

import com.google.gson.annotations.SerializedName
import com.nikita.nullidea.db.UserEntity
import com.nikita.nullidea.model.BaseResponseModel
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {

    @POST("user/login")
    suspend fun signIn(@Body userRequestModel: UserRequestModel): BaseResponseModel<UserEntity>

    @POST("user/register")
    suspend fun signUp(@Body userRequestModel: UserRequestModel): BaseResponseModel<UserEntity>

    @POST("user/verify")
    suspend fun verifyEmail(@Body userVrtRequest: UserVrtRequest): BaseResponseModel<*>

}

data class UserRequestModel(
    val email: String? = null,
    val password: String? = null,
    @SerializedName("auth_firebase_token")
    val authFirebaseToken: String? = null,
    @SerializedName("push_firebase_token")
    val pushFirebaseToken: String? = null
)

data class UserVrtRequest(
    val email: String,
    @SerializedName("verification_code")
    val verificationCode: String? = null
)