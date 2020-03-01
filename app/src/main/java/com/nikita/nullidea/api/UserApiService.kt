/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.api

import com.google.gson.annotations.SerializedName
import com.nikita.nullidea.db.UserEntity
import com.nikita.nullidea.model.BaseResponseModel
import retrofit2.http.*

interface UserApiService {

    @GET("users/")
    suspend fun signIn(@Query("email") email: String, @Query("password") password: String): BaseResponseModel<UserEntity>

    @GET("users/firebase/")
    suspend fun signIn(@Query("auth_firebase_token") firebaseUUID: String): BaseResponseModel<UserEntity>

    @POST("users")
    suspend fun signUp(@Body userRequestModel: UserRequestModel): BaseResponseModel<UserEntity>

    @POST("users/verify")
    suspend fun sendVerifyEmail(@Body userRequestModel: UserRequestModel): BaseResponseModel<*>

    @GET("users/verify/")
    suspend fun verifyEmail(@Query("email") email: String, @Query("code") code: String): BaseResponseModel<*>

    @GET("users/")
    suspend fun isEmailExits(@Query("email") email: String): BaseResponseModel<*>

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
    @SerializedName("code")
    val verificationCode: String? = null
)