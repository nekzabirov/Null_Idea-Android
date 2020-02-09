/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.screen.sign_up

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding.widget.RxTextView

import com.nikita.nullidea.R
import com.nikita.nullidea.TAG
import com.nikita.nullidea.unit.MyFragment
import com.nikita.nullidea.unit.setIconStates
import com.nikita.nullidea.unit.tool.MyLog
import kotlinx.android.synthetic.main.sign_in_fragment.*
import rx.Observable

class SignUpFragment : MyFragment() {

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_in_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signin_helper_txt.setText(R.string.already_have_an_account)
        signin_forgotpassword_btn.isEnabled = false
        signin_forgotpassword_btn.visibility = View.INVISIBLE
        signin_signup_btn.setText(R.string.sign_in)
        signin_login_btn.setText(R.string.sign_up)

        setIconStates(signin_email, signin_email_txtinputlayout)
        setIconStates(signin_password, signin_password_txtinputlayout)

        signin_login_btn.setOnClickListener {

            signin_login_btn.openProgress()

            viewModel.signUp(
                signin_email.text.toString(),
                signin_password.text.toString()
            )

        }

        val emailTxtObs = RxTextView.textChanges(signin_email)
            .map {
                return@map !it.isNullOrEmpty()
                        && android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
            }

        val passwordTxtObs = RxTextView.textChanges(signin_password)
            .map {
                return@map it.length >= 8
            }

        Observable.merge(
            emailTxtObs,
            passwordTxtObs
        )
            .subscribe {
                signin_login_btn.isEnabled = it
            }

        signin_signup_btn.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

        viewModel.internetErrorLiveData.observe(this.viewLifecycleOwner, internetError)

        viewModel.successLiveData.observe(this.viewLifecycleOwner, successObs)
    }

    private val successObs = Observer<Boolean?> {
        if (it == null)
            return@Observer

        signin_login_btn.closeProgress()

        if(it) {
            MyLog.e(TAG, "success sign up")
            //TODO(Open main screen)
        }  else {
            signin_email_txtinputlayout.error = getString(R.string.user_exis)
        }
    }


}
