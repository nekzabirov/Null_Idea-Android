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
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding.widget.RxTextView

import com.nikita.nullidea.R
import com.nikita.nullidea.TAG
import com.nikita.nullidea.db.UserEntity
import com.nikita.nullidea.unit.tool.MyLog
import com.nikita.nullidea.unit.tool.PreferenceTools
import kotlinx.android.synthetic.main.sign_in_fragment.*
import rx.Observable

class SignInFragment : Fragment() {

    override fun onStart() {
        super.onStart()
        MyLog.d(TAG, "onStart")

        viewModel.successLogin.observe(this.viewLifecycleOwner, loginStateObs)

        viewModel.internetErrorLiveData.observe(this.viewLifecycleOwner, internetError)
    }

    companion object {
        fun newInstance() = SignInFragment()
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

    private val internetError = Observer<Any> {
        signin_login_btn.closeProgress()

        Snackbar.make(signin_login_btn, R.string.lost_connection, Snackbar.LENGTH_SHORT).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)
    }

    private fun setIconStates(txtField: TextInputEditText, txtLayout: TextInputLayout) {
        val activeColor = ColorStateList.valueOf(this.resources.getColor(R.color.colorPrimary))
        val disableColor = ColorStateList.valueOf(this.resources.getColor(R.color.friar_gray))

        txtField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                txtLayout.setStartIconTintList(
                    activeColor
                )
                txtLayout.setEndIconTintList(
                    activeColor
                )
            } else {
                txtLayout.setStartIconTintList(
                    disableColor
                )
                txtLayout.setEndIconTintList(
                    disableColor
                )
            }
        }

    }

}
