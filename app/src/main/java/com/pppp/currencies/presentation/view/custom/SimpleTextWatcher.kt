package com.pppp.currencies.presentation.view.custom

import android.text.Editable
import android.text.TextWatcher


open class SimpleTextWatcher(private val handler: (String) -> Unit) : TextWatcher {
    override fun afterTextChanged(editable: Editable?) {
        editable.let { handler(it.toString()) }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }
}