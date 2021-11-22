package com.anailies.userapp.ui.common

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.google.android.material.snackbar.Snackbar

@BindingAdapter("isVisible")
fun setIsVisible(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter("showError")
fun showError(view: View?, error: String?) {
    view?:return
    error?:return

    Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show()
}

