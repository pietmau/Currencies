package com.pppp.currencies.presentation.view.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText


class CurrencyEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        inputType = android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        filters = arrayOf(DecimalDigitsInputFilter(4, 2))
    }
}