/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.google.android.material.button.MaterialButton
import com.nikita.nullidea.R

class MyBtnProgress @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var btn: MaterialButton
    private var pb: ProgressBar

    init {
        val rootView = LayoutInflater.from(context).inflate(
            R.layout.view_my_btn_progress,
            this,
            false
        )
        addView(rootView)

        btn = findViewById(R.id.mybtnprogress_btn)
        pb = findViewById(R.id.mybtnprogress_progress)
    }

    private val duration = 500.toLong()

    override fun setOnClickListener(l: OnClickListener?) {
        //super.setOnClickListener(l)
        btn.setOnClickListener(l)
    }

    override fun setEnabled(enabled: Boolean) {
        //super.setEnabled(enabled)
        btn.isEnabled = enabled
    }

    fun openProgress() {
        btn.animate()
            .setDuration(duration)
            .scaleX(0f)
            .alpha(0f)
            .start()

        pb.animate()
            .setDuration(duration)
            .scaleX(1f)
            .alpha(1f)
            .start()
    }

    fun closeProgress() {
        pb.animate()
            .setDuration(duration)
            .scaleX(0f)
            .alpha(0f)
            .start()

        btn.animate()
            .setDuration(duration)
            .scaleX(1f)
            .alpha(1f)
            .start()
    }

}