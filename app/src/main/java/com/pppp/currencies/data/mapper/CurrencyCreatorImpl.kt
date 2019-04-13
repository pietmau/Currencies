package com.pppp.currencies.data.mapper

import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.data.rates.RatesResponse
import java.math.BigDecimal


class CurrencyCreatorImpl(private val urlCreator: UrlCreator = UrlCreator()) : CurrencyCreator {

    override fun createRates(
        response: RatesResponse,
        names: Map<String, String>,
        baseAmount: BigDecimal
    ): List<Currency> {
        val baseCurrency = createBaseCurrency(response.base, names, baseAmount)
        val currencies = createOtherCurrencies(response, names, baseAmount)
        return listOf(baseCurrency).plus(currencies)
    }

    private fun createOtherCurrencies(
        response: RatesResponse,
        names: Map<String, String>,
        baseAmount: BigDecimal
    ) =
        response.rates.map { entry -> createOtherCurrency(entry, names, baseAmount) }

    private fun createBaseCurrency(
        symbol: String,
        names: Map<String, String>,
        amount: BigDecimal
    ): Currency {
        val symbol = symbol
        val url = urlCreator.createUrl(symbol)
        val countryName = names[symbol]
        return Currency(symbol, 1.toString(), amount, countryName, url, true)
    }

    private fun createOtherCurrency(
        entry: Map.Entry<String, Double>,
        names: Map<String, String>,
        baseAmount: BigDecimal
    ): Currency {
        val symbol = entry.key
        val url = urlCreator.createUrl(symbol)
        val countryName = names[symbol]
        return Currency(symbol, entry.value.toString(), calculateAmount(), countryName, url)
    }

    private fun calculateAmount() = BigDecimal(1)
}
