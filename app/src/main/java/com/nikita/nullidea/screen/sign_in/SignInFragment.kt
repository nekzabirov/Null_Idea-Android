package com.nikita.nullidea.screen.sign_in

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.jakewharton.rxbinding.widget.RxTextView
import com.nikita.nullidea.R
import com.nikita.nullidea.TAG
import com.nikita.nullidea.screen.base_login.BaseLoginFragment
import com.nikita.nullidea.unit.MyFragment
import com.nikita.nullidea.unit.setIconStates
import com.nikita.nullidea.unit.tool.MyLog
import com.nikita.nullidea.unit.tool.PreferenceTools
import kotlinx.android.synthetic.main.sign_in_fragment.*
import rx.Observable

class SignInFragment : BaseLoginFragment() {

    private lateinit var viewModel: SignInViewModel

    private val loginStateObs = Observer<Boolean?> {
        if (it == null)
            return@Observer

        signin_login_btn.closeProgress()

        if (it) {
            Toast.makeText(this.context, "Success sign in/up", Toast.LENGTH_LONG).show()
            MyLog.d(TAG, "on success login")
            PreferenceTools.setUserSigned()
        } else {
            signin_password_txtinputlayout.setStartIconTintList(
                ColorStateList.valueOf(context!!.resources!!.getColor(R.color.error_prime))
            )
            signin_password.setTextColor(
                ColorStateList.valueOf(context!!.resources!!.getColor(R.color.error_prime))
            )
            signin_password_txtinputlayout.error = getString(R.string.wrong_password)
            MyLog.d(TAG, "on error login")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.sign_in_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signin_login_btn.setOnClickListener {

            //signin_login_btn.openProgress()

            viewModel.signIn(
                signin_email.text.toString(),
                signin_password.text.toString()
                )

        }

        signin_login_btn.setText(R.string.sign_in)

        signin_signup_btn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        signin_forgotpassword_btn.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_resetPasswordFragment)
        }
    }

    override fun onSocialLogined(account: GoogleSignInAccount) {
        viewModel.firebaseAuthWithGoogle(account, activity!!)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)

        viewModel.successLogin.observe(this.viewLifecycleOwner, loginStateObs)

        viewModel.internetErrorLiveData.observe(this.viewLifecycleOwner, internetError)
    }

    override fun onInternetError() {
        super.onInternetError()
        signin_login_btn.closeProgress()
    }

    override fun onStart() {
        super.onStart()
        MyLog.d(TAG, "onStart")
    }

}