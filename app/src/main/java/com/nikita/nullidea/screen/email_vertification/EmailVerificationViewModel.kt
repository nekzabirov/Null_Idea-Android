/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.screen.email_vertification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikita.nullidea.repository.UserRepository
import com.nikita.nullidea.unit.MyViewModel
import com.nikita.nullidea.unit.Threads
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EmailVerificationViewModel : MyViewModel() {

    private val userRepository = UserRepository()

    val onStatus = MutableLiveData<Boolean?>()

    fun verificationCode(email: String, code: String) {
        GlobalScope.launch(Threads.ioDispatcher + errorHandler) {
            val checkVerifyEmail =
                userRepository.checkVerifyEmail(email, code)

            onStatus.postValue(checkVerifyEmail.isSuccess)
        }
    }

    fun resendCode(email: String) {
        GlobalScope.launch(Threads.ioDispatcher + errorHandler) {
            userRepository.sendVerifyEmail(email)
        }
    }

}
