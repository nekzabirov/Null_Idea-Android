package com.nikita.nullidea

import android.app.Application
import com.nikita.nullidea.unit.tool.DBTools
import com.nikita.nullidea.unit.tool.MyLog
import com.nikita.nullidea.unit.tool.PreferenceTools

val Any.TAG : String
    get()  {
        return this::class.java.simpleName
    }

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MyLog.init(this)
        PreferenceTools.init(this)
        //DBTools.init(this)
    }
}