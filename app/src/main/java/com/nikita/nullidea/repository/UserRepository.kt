package com.nikita.nullidea.repository

import com.nikita.nullidea.api.UserApiService
import com.nikita.nullidea.api.UserRequestModel
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
            UserRequestModel(email, password)
        )

        if (responseModel.isSuccess && responseModel.data != null) {
            PreferenceTools.setUserSigned()
            //TODO(Save user in db)
        }

        return responseModel
    }

    suspend fun signUp(email: String, password: String): BaseResponseModel<UserEntity> {
        val responseModel = userService.signUp(
            UserRequestModel(email, password)
        )

        if (responseModel.isSuccess && responseModel.data != null) {
            PreferenceTools.setUserSigned()
            //TODO(Save user in db)
        }

        return responseModel
    }

}