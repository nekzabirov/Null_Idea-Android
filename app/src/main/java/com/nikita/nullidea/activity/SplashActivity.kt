package com.nikita.nullidea.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.nikita.nullidea.R
import com.nikita.nullidea.TAG
import com.nikita.nullidea.unit.Threds
import com.nikita.nullidea.unit.tool.MyLog
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        MyLog.d(TAG, "onStart")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        toFullScreen()

        GlobalScope.async(Dispatchers.Main) {
            withContext(Dispatchers.IO) { delay(2000)}

            withContext(Dispatchers.Main) {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
        }

    }


}

fun AppCompatActivity.toFullScreen() {
    this.window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}
