package com.pppp.currencies.data.repository

import java.math.BigDecimal

class AmountCalculator {

    /**
     * We don't do any rounding
     */
    fun calculateAmount(rate: String, baseAmount: BigDecimal) =
        baseAmount.times(rate.toBigDecimal())

    fun parseAmount(amount: String) = try {
        BigDecimal(amount)
    } catch (e: Exception) {
        null
    }

}

