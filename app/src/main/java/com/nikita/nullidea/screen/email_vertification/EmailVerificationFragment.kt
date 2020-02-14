/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.screen.email_vertification

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nikita.nullidea.R
import com.nikita.nullidea.unit.BestTimer
import kotlinx.android.synthetic.main.email_vertification_fragment.*

class EmailVerificationFragment : Fragment() {

    private lateinit var viewModel: EmailVerificationViewModel

    private val bestTimer = BestTimer(120).apply {
        onTime = {
            emailverti_resend_timer.text = it.toString()
        }
        onTimeDone = {
            emailverti_resend_timer.setTextColor(resources.getColor(R.color.colorPrimary))
            emailverti_resend_btn.setTextColor(resources.getColor(R.color.colorPrimary))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.email_vertification_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bestTimer.start()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EmailVerificationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
