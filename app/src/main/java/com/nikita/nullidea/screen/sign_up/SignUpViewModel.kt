/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.screen.sign_up

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.nikita.nullidea.TAG
import com.nikita.nullidea.repository.UserRepository
import com.nikita.nullidea.screen.base_login.LoginViewModel
import com.nikita.nullidea.unit.MyViewModel
import com.nikita.nullidea.unit.Threads
import com.nikita.nullidea.unit.tool.MyLog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUpViewModel : LoginViewModel() {

    val successLiveData = successLogin

    fun sendCode(email: String) {
        GlobalScope.launch(Threads.ioDispatcher + errorHandler) {
            val verifyEmail = userRepository.sendVerifyEmail(email)
            successLiveData.postValue(verifyEmail.isSuccess)
        }
    }

}
