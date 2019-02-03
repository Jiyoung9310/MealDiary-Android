package com.teamnexters.android.mealdiary.util.extension

import android.content.Context
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.showKeyboard(delay: Long = 0) {
    Handler().postDelayed({
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }, delay)
}

fun View.hideKeyboard(delay: Long = 0) {
    Handler().postDelayed({
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }, delay)
}
