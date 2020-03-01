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
import com.nikita.nullidea.screen.base_login.LoginViewModel
import com.nikita.nullidea.unit.MyViewModel
import com.nikita.nullidea.unit.Threads
import com.nikita.nullidea.unit.tool.MyLog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EmailVerificationViewModel : LoginViewModel() {

    val onStatus = MutableLiveData<Boolean?>()

    fun verificationCode(email: String, code: String) {
        MyLog.d(TAG, "onVerificationCode")
        GlobalScope.launch(Threads.ioDispatcher + errorHandler) {
            val checkVerifyEmail =
                userRepository.checkVerifyEmail(email, code)

            onStatus.postValue(checkVerifyEmail.isSuccess)
            isEmailApproveLive.postValue(checkVerifyEmail.isSuccess)
        }
    }

    fun resendCode(email: String) {
        MyLog.d(TAG, "onResendCode")
        GlobalScope.launch(Threads.ioDispatcher + errorHandler) {
            userRepository.sendVerifyEmail(email)
        }
    }

}
