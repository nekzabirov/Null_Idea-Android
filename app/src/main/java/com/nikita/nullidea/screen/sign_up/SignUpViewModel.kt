/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.screen.sign_up

import androidx.lifecycle.MutableLiveData
import com.nikita.nullidea.repository.UserRepository
import com.nikita.nullidea.unit.MyViewModel
import com.nikita.nullidea.unit.Threads
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUpViewModel : MyViewModel() {

    private val userRepository = UserRepository()

    val successLiveData = MutableLiveData<Boolean?>()

    fun signUp(email: String, password: String) {
        GlobalScope.launch(Threads.ioDispatcher + errorHandler) {
            val responseModel = userRepository.signUp(email, password)

            successLiveData.postValue(responseModel.isSuccess)
        }
    }

}
