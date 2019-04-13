package com.pppp.currencies.data.mapper

import java.math.BigDecimal

class AmountCalculator {

    /**
     * We don't do any rounding
     */
    fun calculateAmount(rate: String, baseAmount: BigDecimal) =
        baseAmount.times(rate.toBigDecimal())
}

