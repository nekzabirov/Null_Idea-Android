/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.screen.resetpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikita.nullidea.repository.UserRepository
import com.nikita.nullidea.screen.base_login.LoginViewModel
import com.nikita.nullidea.unit.Threads
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ResetPasswordViewModel : LoginViewModel() {


    val isEmailExitsLive = MutableLiveData<Boolean?>()

    val onSuccessLiveData = MutableLiveData<Boolean?>()

    fun checkEmail(email: String) {
        GlobalScope.launch(Threads.ioDispatcher + errorHandler) {
            isEmailExitsLive.postValue(userRepository.isEmailExits(email))
        }
    }

    fun satNewPassword(email: String, password: String) {
        GlobalScope.launch(Threads.ioDispatcher + errorHandler) {
            onSuccessLiveData.postValue(userRepository.resetPassword(email, password))
        }
    }

}
