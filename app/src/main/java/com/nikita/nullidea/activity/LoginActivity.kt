package com.nikita.nullidea.activity

import android.os.Bundle
import com.nikita.nullidea.R

class LoginActivity : MyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        toFullScreen()
    }

}
