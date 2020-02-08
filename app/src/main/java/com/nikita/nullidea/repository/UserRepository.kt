package com.nikita.nullidea.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserRepository: BaseRepository() {

    suspend fun getFirebaseToken(): String = suspendCoroutine { continuation ->
        FirebaseInstanceId.getInstance()
            .instanceId
            .addOnCompleteListener(
                OnCompleteListener { task ->

                if (!task.isSuccessful) {
                    continuation.resumeWithException(
                        IllegalArgumentException("Cannot load firebase token")
                    )
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                if (token == null) {
                    continuation.resumeWithException(
                        IllegalArgumentException("Firebase token is null")
                    )
                    return@OnCompleteListener
                }

                continuation.resume(token)
            }
            )
    }

}