package com.pppp.currencies.data.mapper

import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.data.rates.CurrenciesResponse
import java.math.BigDecimal

interface CurrencyCreator {

    fun createCurrency(
        response: CurrenciesResponse,
        names: Map<String, String>,
        second: BigDecimal
    ): List<Currency>
}
