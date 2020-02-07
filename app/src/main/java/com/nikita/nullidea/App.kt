package com.nikita.nullidea

import android.app.Application
import com.nikita.nullidea.unit.tool.DBTools
import com.nikita.nullidea.unit.tool.PreferenceTools

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferenceTools.init(this)
        //DBTools.init(this)
    }
}