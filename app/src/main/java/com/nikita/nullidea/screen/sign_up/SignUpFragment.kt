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
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.jakewharton.rxbinding.widget.RxTextView

import com.nikita.nullidea.R
import com.nikita.nullidea.TAG
import com.nikita.nullidea.screen.base_login.BaseLoginFragment
import com.nikita.nullidea.unit.MyFragment
import com.nikita.nullidea.unit.setIconStates
import com.nikita.nullidea.unit.tool.MyLog
import kotlinx.android.synthetic.main.sign_in_fragment.*
import rx.Observable

class SignUpFragment : BaseLoginFragment() {

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_in_fragment, container, false)
    }

    private fun initUI() {
        signin_helper_txt.setText(R.string.already_have_an_account)
        signin_forgotpassword_btn.isEnabled = false
        signin_forgotpassword_btn.visibility = View.INVISIBLE
        signin_signup_btn.setText(R.string.sign_in)
        signin_login_btn.setText(R.string.sign_up)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        signin_login_btn.setOnClickListener(signUP)

        signin_signup_btn.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    private val signUP: (View) -> Unit = {

        signin_login_btn.openProgress()

        viewModel.sendCode(
            signin_email.text.toString()
        )

    }

    override fun onSocialLogined(account: GoogleSignInAccount) {
        viewModel.firebaseAuthWithGoogle(account, activity!!)
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
            MyLog.e(TAG, "success sign up/in")
            openVerifiScreen()

        }  else {
            signin_email_txtinputlayout.error = getString(R.string.user_exis)
        }
    }

    private fun openVerifiScreen() {
        val args = Bundle().apply {
            putString(this@SignUpFragment.getString(R.string.user_email_key), signin_email.text.toString())
            putString(this@SignUpFragment.getString(R.string.user_password_key), signin_password.text.toString())
        }
        Navigation.findNavController(signin_login_btn)
            .navigate(
                R.id.action_signUpFragment_to_emailVerificationFragment,
                args
            )
    }


}
