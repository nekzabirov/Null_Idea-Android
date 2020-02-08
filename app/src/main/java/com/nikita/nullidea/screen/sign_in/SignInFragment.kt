package com.nikita.nullidea.screen.sign_in

import android.content.res.ColorStateList
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

import com.nikita.nullidea.R
import com.nikita.nullidea.TAG
import com.nikita.nullidea.unit.tool.MyLog
import kotlinx.android.synthetic.main.sign_in_fragment.*

class SignInFragment : Fragment() {

    override fun onStart() {
        super.onStart()
        MyLog.d(TAG, "onStart")
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setIconStates(txtField: TextInputEditText, txtLayout: TextInputLayout) {
        txtField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                txtLayout.setStartIconTintList(
                    ColorStateList.valueOf(this.resources.getColor(R.color.colorPrimary))
                )
            else
                txtLayout.setStartIconTintList(
                    ColorStateList.valueOf(this.resources.getColor(R.color.friar_gray))
                )
        }
    }

}
