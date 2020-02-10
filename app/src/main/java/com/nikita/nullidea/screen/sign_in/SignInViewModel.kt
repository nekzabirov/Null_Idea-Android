package com.nikita.nullidea.screen.sign_in

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.nikita.nullidea.TAG
import com.nikita.nullidea.db.UserEntity
import com.nikita.nullidea.repository.UserRepository
import com.nikita.nullidea.unit.MyViewModel
import com.nikita.nullidea.unit.OnError
import com.nikita.nullidea.unit.Threads
import com.nikita.nullidea.unit.tool.MyLog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignInViewModel : MyViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val userRepository = UserRepository()

    val successLogin = MutableLiveData<Boolean?>()

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

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount, activity: Activity) {
        MyLog.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    MyLog.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser

                    signInByFirebase(user!!.uid)
                }
                else {
                    // If sign in fails, display a message to the user.
                    MyLog.e(TAG, "signInWithCredential:failure", task.exception)
                    internetErrorLiveData.postValue(null)
                }
            }
    }

    private fun signInByFirebase(uid: String) {

    }

}
