/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.unit

import android.content.res.ColorStateList
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.nikita.nullidea.R

abstract class MyFragment : Fragment() {

    open val internetError = Observer<Any> {
        Snackbar.make(this.view!!, R.string.lost_connection, Snackbar.LENGTH_SHORT).show()
    }


}

fun Fragment.setIconStates(txtField: TextInputEditText, txtLayout: TextInputLayout) {
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
