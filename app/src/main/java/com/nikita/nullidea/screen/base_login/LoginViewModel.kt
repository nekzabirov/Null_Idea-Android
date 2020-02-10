/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.screen.base_login

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.nikita.nullidea.TAG
import com.nikita.nullidea.db.UserEntity
import com.nikita.nullidea.repository.UserRepository
import com.nikita.nullidea.unit.MyViewModel
import com.nikita.nullidea.unit.Threads
import com.nikita.nullidea.unit.tool.MyLog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class LoginViewModel: MyViewModel() {

    open val userRepository = UserRepository()

    open val successLogin = MutableLiveData<Boolean?>()

    open val userEntityLive = MutableLiveData<UserEntity?>()

    private val auth = FirebaseAuth.getInstance()

    open fun firebaseAuthWithGoogle(acct: GoogleSignInAccount, activity: Activity) {
        MyLog.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    MyLog.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser

                    if (user == null) {
                        MyLog.e(TAG, "user is null", task.exception)
                        internetErrorLiveData.postValue(null)
                        return@addOnCompleteListener
                    }

                    signInByFirebase(user)
                }
                else {
                    // If sign in fails, display a message to the user.
                    MyLog.e(TAG, "signInWithCredential:failure", task.exception)
                    internetErrorLiveData.postValue(null)
                }
            }
    }

    open fun signInByFirebase(user: FirebaseUser) {
        MyLog.d(TAG, "start sign in via firebase")
        GlobalScope.launch(Threads.ioDispatcher + errorHandler) {

            val userResponse = userRepository.signInFirebase(user.uid)

            when {
                userResponse.isSuccess -> {
                    MyLog.d(this@LoginViewModel.TAG, "success login via social")
                    successLogin.postValue(userResponse.isSuccess)
                    userEntityLive.postValue(userResponse.data)
                }
                user.email != null -> {
                    signUpByFirebase(user)
                }
                else -> {
                    MyLog.e(this@LoginViewModel.TAG, "error login via social ${userResponse.error}")
                    successLogin.postValue(userResponse.isSuccess)
                }
            }

        }
    }

    open fun signUpByFirebase(user: FirebaseUser) {
        if (user.email == null) {
            internetErrorLiveData.postValue(null)
            return
        }
        MyLog.d(TAG, "start sign up via firebase")
        GlobalScope.launch(Threads.ioDispatcher + errorHandler) {

            val userResponse = userRepository.signUpFirebase(user.uid, user.email!!)

            if (userResponse.isSuccess) {
                //TODO(Send update user info)
                MyLog.d(this@LoginViewModel.TAG, "success register via social")
                successLogin.postValue(userResponse.isSuccess)
                userEntityLive.postValue(userResponse.data)
            }
            else {
                MyLog.d(this@LoginViewModel.TAG, "error register via social ${userResponse.error}")
                successLogin.postValue(userResponse.isSuccess)
            }

        }
    }
}