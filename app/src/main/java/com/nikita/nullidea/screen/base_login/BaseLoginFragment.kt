/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.screen.base_login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.jakewharton.rxbinding.widget.RxTextView
import com.nikita.nullidea.R
import com.nikita.nullidea.TAG
import com.nikita.nullidea.unit.MyFragment
import com.nikita.nullidea.unit.setIconStates
import com.nikita.nullidea.unit.tool.MyLog
import kotlinx.android.synthetic.main.sign_in_fragment.*
import rx.Observable

abstract class BaseLoginFragment : MyFragment() {
    private val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private val googleSignInClient by lazy {
        GoogleSignIn.getClient(this.activity!!, gso)
    }

    private val SIGN_IN_GOOGLE = 22
    private val SIGN_IN_FACEBOOK = 23
    private val SIGN_IN_GITHUB = 24

    private fun initFieldsListener() {
        val emailTxtObs = RxTextView.textChanges(signin_email)
        val passwordTxtObs = RxTextView.textChanges(signin_password)

        Observable.merge(
            emailTxtObs,
            passwordTxtObs
        )
            .map {
                return@map (!it.isNullOrEmpty()
                        && android.util.Patterns.EMAIL_ADDRESS.matcher(signin_email.text.toString()).matches())
                        && (!signin_password.text.isNullOrEmpty() && signin_password.text?.length!! >= 8)
            }
            .subscribe {
                signin_login_btn.isEnabled = it
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setIconStates(signin_email, signin_email_txtinputlayout)
        setIconStates(signin_password, signin_password_txtinputlayout)

        initFieldsListener()

        signin_google_sign.setOnClickListener {

            MyLog.d(TAG, "start sign in via google")

            //signin_login_btn.openProgress()

            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, SIGN_IN_GOOGLE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode in 22..24) {
            var account: GoogleSignInAccount? = null

            if (requestCode == SIGN_IN_GOOGLE) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                account = task.getResult(ApiException::class.java)
            }

            if (account == null) {
                onInternetError()
                return
            }
            onSocialLogined(account)
        }

    }

    abstract fun onSocialLogined(account: GoogleSignInAccount)

}