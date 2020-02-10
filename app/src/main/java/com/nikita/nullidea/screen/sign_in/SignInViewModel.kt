package com.nikita.nullidea.screen.sign_in

import androidx.lifecycle.MutableLiveData
import com.nikita.nullidea.db.UserEntity
import com.nikita.nullidea.repository.UserRepository
import com.nikita.nullidea.screen.base_login.LoginViewModel
import com.nikita.nullidea.unit.MyViewModel
import com.nikita.nullidea.unit.Threads
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignInViewModel : LoginViewModel() {

    fun signIn(email: String, password: String) {

        GlobalScope.launch(Threads.ioDispatcher + errorHandler) {

            val userResponse = userRepository.signIn(email, password)

            successLogin.postValue(userResponse.isSuccess)

            if (userResponse.isSuccess) {
                userEntityLive.postValue(userResponse.data)
            }

        }

    }

}
