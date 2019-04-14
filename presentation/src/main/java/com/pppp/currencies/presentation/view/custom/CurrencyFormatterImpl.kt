package com.pppp.currencies.presentation.view.custom

import java.math.BigDecimal
import java.math.RoundingMode

class CurrencyFormatterImpl : CurrencyFormatter {

    override fun format(bigDecimal: BigDecimal): String {
        return bigDecimal.setScale(4, RoundingMode.HALF_EVEN).toString()
    }
}

interface CurrencyFormatter {

    fun format(bigDecimal: BigDecimal): String
}
