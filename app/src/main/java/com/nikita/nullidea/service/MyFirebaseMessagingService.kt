/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.nikita.nullidea.unit.tool.PreferenceTools

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        PreferenceTools.setFirebaseToken(token)
    }

}