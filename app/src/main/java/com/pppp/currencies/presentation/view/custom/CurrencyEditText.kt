package com.pppp.currencies.presentation.view.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText


class CurrencyEditText constructor(
    context: Context,
    attrs: AttributeSet
) : AppCompatEditText(context, attrs) {

    init {
        filters = arrayOf(DecimalDigitsInputFilter(4, 2))
    }
}