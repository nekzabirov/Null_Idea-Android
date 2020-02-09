package com.nikita.nullidea.screen.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikita.nullidea.db.UserEntity
import com.nikita.nullidea.repository.UserRepository
import com.nikita.nullidea.unit.OnError
import com.nikita.nullidea.unit.Threads
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private val errorHandler = Threads.getErrorHandler(object : OnError {
        override fun onInternet() {
            internetErrorLiveData.postValue(null)
        }
    })

    private val userRepository = UserRepository()

    val successLogin = MutableLiveData<Boolean?>()

    val internetErrorLiveData = MutableLiveData<Any>()

    private val userEntityLive = MutableLiveData<UserEntity?>()

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
