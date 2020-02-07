package com.nikita.nullidea

import android.app.Application
import com.nikita.nullidea.unit.PreferenceTools

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferenceTools.init(this)
    }
}