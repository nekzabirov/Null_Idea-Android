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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nikita.nullidea.R
import com.nikita.nullidea.databinding.ResetPasswordFragmentBinding
import com.nikita.nullidea.screen.email_vertification.EmailVerificationViewModel

class ResetPasswordFragment : Fragment() {

    private lateinit var mViewModel: ResetPasswordViewModel
    private lateinit var emailVerificationViewModel: EmailVerificationViewModel
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
    }

    private val onCheckEmail = View.OnClickListener {
        mViewModel.checkEmail(binding.resetpasswordEmailField.text.toString())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(ResetPasswordViewModel::class.java)
        emailVerificationViewModel = ViewModelProviders.of(this.requireActivity()).get(EmailVerificationViewModel::class.java)

        mViewModel.isEmailExitsLive.observe(viewLifecycleOwner, isEmailExitsObs)
        emailVerificationViewModel.isEmailApproveLive.observe(viewLifecycleOwner, Observer {
            binding.isEmailApprove = it
        })
    }

    private val isEmailExitsObs = Observer<Boolean?> {
        if (it == null)
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

    }

}
