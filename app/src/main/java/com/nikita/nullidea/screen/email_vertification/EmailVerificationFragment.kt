/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.screen.email_vertification

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

import com.nikita.nullidea.R
import com.nikita.nullidea.TAG
import com.nikita.nullidea.unit.BestTimer
import com.nikita.nullidea.unit.MyFragment
import com.nikita.nullidea.unit.tool.MyLog
import kotlinx.android.synthetic.main.email_vertification_fragment.*

class EmailVerificationFragment : MyFragment() {

    private lateinit var viewModel: EmailVerificationViewModel

    private val userEmail: String by lazy {
        arguments?.getString(getString(R.string.user_email_key), null)!!
    }

    private val resendTime = 120

    private val bestTimer = BestTimer(resendTime).apply {
        onTime = {
            emailverti_resend_timer.text = it.toString()
        }
        onTimeDone = {
            emailverti_resend_timer.setTextColor(resources.getColor(R.color.colorPrimary))
            emailverti_resend_btn.setTextColor(resources.getColor(R.color.colorPrimary))
            emailverti_resend_btn.isEnabled = true
        }
    }

    private val statusObserver = Observer<Boolean?> {status ->
        emailverti_send_btn.closeProgress()
        status?.let {
            if (it) {
                onSuccess()
            } else {
                emailverti_code.color = resources.getColor(R.color.error_prime)
                emailverti_code.postInvalidate()
                showWrongCodeMsg()
            }
        }
    }

    private fun onSuccess() {
        activity?.onBackPressed()
    }

    private fun showWrongCodeMsg() {
        MyLog.e(TAG, "onWrong code")
        Snackbar.make(emailverti_code, R.string.wrong_code, Snackbar.LENGTH_SHORT).apply {
            setBackgroundTint(resources.getColor(R.color.error_prime))
        }.show()
    }

    private val checkCode: (View) -> Unit = {
        //emailverti_send_btn.openProgress()

        emailverti_code.color = resources.getColor(R.color.colorPrimary)
        //emailverti_code.postInvalidate()

        viewModel.verificationCode(userEmail, emailverti_code.text)

        MyLog.d(TAG, "onCheckCode clickced")
    }

    private val resendCode: (View) -> Unit = {
        viewModel.resendCode(email = userEmail)

        emailverti_resend_btn.isEnabled = false

        startTimer()
    }

    private val codeInputListener: (Boolean) -> Unit = {
        //emailverti_code.color = resources.getColor(R.color.colorPrimary)
        emailverti_send_btn.isEnabled = it
    }

    private fun startTimer() {
        MyLog.d(TAG, "on start timer")
        bestTimer.start()
        emailverti_resend_btn.setTextColor(resources.getColor(R.color.full_gray))
        emailverti_resend_timer.setTextColor(resources.getColor(R.color.full_gray))
        emailverti_resend_timer.text = (resendTime).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.email_vertification_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailverti_send_btn.isEnabled = false

        emailverti_code.onListener = codeInputListener

        emailverti_send_btn.setOnClickListener(checkCode)
        emailverti_resend_btn.setOnClickListener(resendCode)

        startTimer()

        emailverti_send_btn.setText(R.string.proceed)

    }

    override fun onInternetError() {
        super.onInternetError()
        emailverti_send_btn.closeProgress()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(EmailVerificationViewModel::class.java)
        viewModel.onStatus.observe(this.viewLifecycleOwner, statusObserver)
        viewModel.resendCode(email = userEmail)
    }

    override fun onDestroy() {
        super.onDestroy()
        bestTimer.stop()
    }

    override fun onStart() {
        super.onStart()
        MyLog.d(TAG, "onStart")
    }

}
