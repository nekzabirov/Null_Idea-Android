package com.nikita.nullidea.screen.sign_in

import android.content.res.ColorStateList
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding.widget.RxTextView

import com.nikita.nullidea.R
import com.nikita.nullidea.TAG
import com.nikita.nullidea.db.UserEntity
import com.nikita.nullidea.unit.MyFragment
import com.nikita.nullidea.unit.setIconStates
import com.nikita.nullidea.unit.tool.MyLog
import com.nikita.nullidea.unit.tool.PreferenceTools
import kotlinx.android.synthetic.main.sign_in_fragment.*
import rx.Observable

class SignInFragment : MyFragment() {

    override fun onStart() {
        super.onStart()
        MyLog.d(TAG, "onStart")
    }

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_in_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setIconStates(signin_email, signin_email_txtinputlayout)
        setIconStates(signin_password, signin_password_txtinputlayout)

        signin_login_btn.setOnClickListener {

            signin_login_btn.openProgress()

            viewModel.signIn(
                signin_email.text.toString(),
                signin_password.text.toString()
                )

        }

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

        signin_signup_btn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_signInFragment_to_signUpFragment)
        }

    }

    private val loginStateObs = Observer<Boolean?> {
        if (it == null)
            return@Observer

        signin_login_btn.closeProgress()

        if (it) {
            MyLog.d(TAG, "on success login")
            PreferenceTools.setUserSigned()
        } else {
            signin_password_txtinputlayout.error = getString(R.string.wrong_password)
            MyLog.d(TAG, "on error login")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)

        viewModel.successLogin.observe(this.viewLifecycleOwner, loginStateObs)

        viewModel.internetErrorLiveData.observe(this.viewLifecycleOwner, internetError)
    }

}