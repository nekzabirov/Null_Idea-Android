/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.screen.resetpassword

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding.widget.RxTextView
import com.nikita.nullidea.R
import com.nikita.nullidea.databinding.ResetPasswordFragmentBinding
import com.nikita.nullidea.screen.email_vertification.EmailVerificationViewModel
import com.nikita.nullidea.unit.MyFragment
import rx.Observable

class ResetPasswordFragment : MyFragment() {

    private val mViewModel: ResetPasswordViewModel by lazy { ViewModelProviders.of(this).get(ResetPasswordViewModel::class.java) }
    private val emailVerificationViewModel: EmailVerificationViewModel by lazy { ViewModelProviders.of(this.requireActivity()).get(EmailVerificationViewModel::class.java) }
    private lateinit var binding: ResetPasswordFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ResetPasswordFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.onCheckEmail = onCheckEmail
        binding.onResetPassword = onChangePassword

        binding.resetpasswordEmailField.addTextChangedListener {
            binding.isBtnEnable = android.util.Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches()
        }
    }

    private val onCheckEmail = View.OnClickListener {
        isEmailExits = false
        mViewModel.checkEmail(binding.resetpasswordEmailField.text.toString())
    }

    private val onChangePassword = View.OnClickListener {
        mViewModel.satNewPassword(
            binding.resetpasswordEmailField.text.toString(),
            binding.resetpasswordPasswordFiled.text.toString()
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        emailVerificationViewModel.clear()

        mViewModel.onSuccessLiveData.observe(viewLifecycleOwner, onSuccessChangedObservable)

        mViewModel.internetErrorLiveData.observe(viewLifecycleOwner, Observer {
            binding.resetpasswordBtn.closeProgress()
            Snackbar.make(view!!, R.string.lost_connection, Snackbar.LENGTH_SHORT).show()
        })

        emailVerificationViewModel.isEmailApproveLive.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.isEmailApprove = it
                binding.isBtnEnable = false

                val password = RxTextView.textChanges(binding.resetpasswordPasswordFiled)
                val passwordReply = RxTextView.textChanges(binding.resetpasswordPasswordreplyFiled)

                Observable.merge(
                    password,
                    passwordReply
                )
                    .map {
                        return@map binding.resetpasswordPasswordFiled.text.toString().length >= 8
                                && binding.resetpasswordPasswordFiled.text.toString() == binding.resetpasswordPasswordreplyFiled.text.toString()
                    }
                    .subscribe { enabled ->
                        binding.isBtnEnable = enabled
                    }

                emailVerificationViewModel.clear()
            }
        })

        mViewModel.isEmailExitsLive.observe(viewLifecycleOwner, isEmailExitsObs)

    }

    private var isEmailExits = false

    private val isEmailExitsObs = Observer<Boolean?> {
        if (it == null)
            return@Observer

        if (binding.isEmailApprove == true || isEmailExits)
            return@Observer

        binding.resetpasswordBtn.closeProgress()

        if (it)
            findNavController().navigate(
                R.id.action_resetPasswordFragment_to_emailVerificationFragment,
                Bundle().apply {
                    putString(getString(R.string.user_email_key), binding.resetpasswordEmailField.text.toString())
                }
            )
         else
            binding.resetpasswordEmailInputlayout.error = getString(R.string.email_not_exits)

        isEmailExits = it
    }

    private val onSuccessChangedObservable = Observer<Boolean?> {
        if (it == null)
            return@Observer

        binding.resetpasswordBtn.closeProgress()

        if (it) {
            Snackbar.make(view!!, "OK", Snackbar.LENGTH_SHORT).show()
            //TODO(Move to login scfeen)
        } else {
            binding.resetpasswordEmailInputlayout.error = ""
        }
    }


}
