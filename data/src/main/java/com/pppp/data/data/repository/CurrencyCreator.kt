package com.pppp.data.data.repository

import com.pppp.currencies.pokos.Currency
import com.pppp.data.data.rates.CurrenciesResponse
import java.math.BigDecimal

interface CurrencyCreator {

    fun createCurrency(
        response: CurrenciesResponse,
        names: Map<String, String>,
        second: BigDecimal
    ): List<Currency>
}
