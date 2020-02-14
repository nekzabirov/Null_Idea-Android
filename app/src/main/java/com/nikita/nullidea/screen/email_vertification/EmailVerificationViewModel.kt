/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.screen.email_vertification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikita.nullidea.TAG
import com.nikita.nullidea.repository.UserRepository
import com.nikita.nullidea.unit.MyViewModel
import com.nikita.nullidea.unit.Threads
import com.nikita.nullidea.unit.tool.MyLog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EmailVerificationViewModel : MyViewModel() {

    private val userRepository = UserRepository()

    val onStatus = MutableLiveData<Boolean?>()

    fun verificationCode(email: String, password: String, code: String) {
        MyLog.d(TAG, "onVerificationCode")
        GlobalScope.launch(Threads.ioDispatcher + errorHandler) {
            val checkVerifyEmail =
                userRepository.checkVerifyEmail(email, code)

            if (checkVerifyEmail.isSuccess) {
                signUp(email, password)
            } else {
                onStatus.postValue(checkVerifyEmail.isSuccess)
            }
        }
    }

    fun resendCode(email: String) {
        MyLog.d(TAG, "onResendCode")
        GlobalScope.launch(Threads.ioDispatcher + errorHandler) {
            userRepository.sendVerifyEmail(email)
        }
    }

    fun signUp(email: String, password: String) {
        MyLog.d(TAG, "onSignUp")
        GlobalScope.launch(Threads.ioDispatcher + errorHandler) {
            val responseModel = userRepository.signUp(email, password)

            onStatus.postValue(responseModel.isSuccess)
        }
    }

}
