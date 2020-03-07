/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.screen.sign_up

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.snackbar.Snackbar

import com.nikita.nullidea.R
import com.nikita.nullidea.TAG
import com.nikita.nullidea.screen.base_login.BaseLoginFragment
import com.nikita.nullidea.screen.email_vertification.EmailVerificationViewModel
import com.nikita.nullidea.unit.tool.MyLog
import kotlinx.android.synthetic.main.sign_in_fragment.*

class SignUpFragment : BaseLoginFragment() {

    private lateinit var viewModel: SignUpViewModel /*by viewModels<SignUpViewModel> {  ViewModelProvider.NewInstanceFactory()}*/
    private val emailVerificationViewModel: EmailVerificationViewModel by lazy { ViewModelProviders.of(requireActivity()).get(EmailVerificationViewModel::class.java) }

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
        signin_login_btn.setText(R.string.next)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        signin_login_btn.setOnClickListener(onNext)

        signin_signup_btn.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    private val onNext: (View) -> Unit = {
        openVerifiScreen()
    }

    private val onSignUp: (View) -> Unit = {
        viewModel.signUp(signin_email.text.toString(), signin_password.text.toString())
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

    override fun onStart() {
        super.onStart()
        if (isNeedToApproveEmail) {
            emailVerificationViewModel.isEmailApproveLive.observe(
                viewLifecycleOwner,
                onEmailApproveObs
            )
            signin_password_txtinputlayout.visibility = View.GONE
            signin_password.setText("12345678")
        }
    }

    private val successObs = Observer<Boolean?> {
        if (it == null)
            return@Observer

        signin_login_btn.closeProgress()

        if (it) {
            Snackbar.make(view!!, "OK", Snackbar.LENGTH_SHORT).show()
        }
    }

    private var isNeedToApproveEmail = true

    private val onEmailApproveObs = Observer<Boolean> {
        if (it) {
            isNeedToApproveEmail = false
            signin_email.isEnabled = false
            signin_password_txtinputlayout.visibility = View.VISIBLE
            signin_login_btn.setOnClickListener(onSignUp)
            signin_login_btn.setText(R.string.sign_up)
            signin_password.text = null

            emailVerificationViewModel.clear()
        }
    }

    private fun openVerifiScreen() {
        val args = Bundle().apply {
            putString(this@SignUpFragment.getString(R.string.user_email_key), signin_email.text.toString())
        }
        Navigation.findNavController(signin_login_btn)
            .navigate(
                R.id.action_signUpFragment_to_emailVerificationFragment,
                args
            )
    }


}
