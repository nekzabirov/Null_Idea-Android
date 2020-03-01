package com.nikita.nullidea.repository

import com.nikita.nullidea.api.UserApiService
import com.nikita.nullidea.api.UserRequestModel
import com.nikita.nullidea.api.UserVrtRequest
import com.nikita.nullidea.db.UserEntity
import com.nikita.nullidea.model.BaseResponseModel
import com.nikita.nullidea.unit.rest.AppRest
import com.nikita.nullidea.unit.tool.PreferenceTools

class UserRepository: BaseRepository() {

    private val userService: UserApiService by lazy {
        AppRest(apiUrl, UserApiService::class.java).api()
    }

    suspend fun signIn(email: String, password: String): BaseResponseModel<UserEntity> {
        val responseModel = userService.signIn(
            email, password
        )

        if (responseModel.isSuccess && responseModel.data != null) {
            PreferenceTools.setUserSigned()
            //TODO(Save user in db)
        }

        return responseModel
    }

    suspend fun signUp(email: String, password: String): BaseResponseModel<UserEntity> {
        val responseModel = userService.signUp(
            UserRequestModel(email = email, password = password)
        )

        if (responseModel.isSuccess && responseModel.data != null) {
            PreferenceTools.setUserSigned()
            //TODO(Save user in db)
        }


        return responseModel
    }

    suspend fun signInFirebase(uuid: String): BaseResponseModel<UserEntity> {
        val responseModel = userService.signIn(
            uuid
        )

        if (responseModel.isSuccess && responseModel.data != null) {
            PreferenceTools.setUserSigned()
            //TODO(Save user in db)
        }

        return responseModel
    }

    suspend fun signUpFirebase(uuid: String, email: String): BaseResponseModel<UserEntity> {
        val responseModel = userService.signUp(
            UserRequestModel(
                email = email,
                authFirebaseToken = uuid)
        )

        if (responseModel.isSuccess && responseModel.data != null) {
            PreferenceTools.setUserSigned()
            //TODO(Save user in db)
        }

        return responseModel
    }

    suspend fun sendVerifyEmail(email: String): BaseResponseModel<*> {
        return userService.sendVerifyEmail(UserRequestModel(email = email))
    }

    suspend fun checkVerifyEmail(email: String, code: String): BaseResponseModel<*> {
        return userService.verifyEmail(email, code)
    }

    suspend fun isEmailExits(email: String): Boolean {
        return userService.isEmailExits(email).isSuccess
    }

    suspend fun resetPassword(email: String, password: String): Boolean {
        return userService.resetPassword(UserRequestModel(email = email, password = password)).isSuccess
    }

}