/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.unit

import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.nikita.nullidea.R

abstract class MyFragment : Fragment() {

    open val internetError = Observer<Any> {
        onInternetError()
    }

    open fun onInternetError() {
        Snackbar.make(this.view!!, R.string.lost_connection, Snackbar.LENGTH_SHORT).show()
    }


}

fun Fragment.setIconStates(txtField: TextInputEditText,
                           txtLayout: TextInputLayout,
                           removeErrorWhileTyping: Boolean = true) {
    val activeColor = ColorStateList.valueOf(this.resources.getColor(R.color.colorPrimary))
    val disableColor = ColorStateList.valueOf(this.resources.getColor(R.color.friar_gray))

    txtField.setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            txtField.setTextColor(
                activeColor
            )
            txtLayout.setStartIconTintList(
                activeColor
            )
            txtLayout.setEndIconTintList(
                activeColor
            )
        } else {
            txtField.setTextColor(
                disableColor
            )
            txtLayout.setStartIconTintList(
                disableColor
            )
            txtLayout.setEndIconTintList(
                disableColor
            )
        }
        txtLayout.error = null
    }

    if (removeErrorWhileTyping) {
        txtField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                txtLayout.error = null

                val color = when (txtField.isFocused) {
                    true -> activeColor
                    false -> disableColor
                }

                txtField.setTextColor(
                    color
                )
                txtLayout.setStartIconTintList(
                    color
                )
                txtLayout.setEndIconTintList(
                    color
                )
            }

        })
    }

}
