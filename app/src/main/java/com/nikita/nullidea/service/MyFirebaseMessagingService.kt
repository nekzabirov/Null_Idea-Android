/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.nikita.nullidea.unit.tool.MyLog
import com.nikita.nullidea.unit.tool.PreferenceTools

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        MyLog.init(applicationContext)
        PreferenceTools.init(applicationContext)

        PreferenceTools.setFirebaseToken(token)
    }

}