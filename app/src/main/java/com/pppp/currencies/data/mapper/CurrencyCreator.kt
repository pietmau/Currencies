package com.pppp.currencies.data.mapper

import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.data.rates.RatesResponse
import java.math.BigDecimal

interface CurrencyCreator {

    fun createRates(
        response: RatesResponse,
        names: Map<String, String>,
        second: BigDecimal
    ): List<Currency>
}
