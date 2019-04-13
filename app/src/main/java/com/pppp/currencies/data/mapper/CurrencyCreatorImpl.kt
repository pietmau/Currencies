package com.pppp.currencies.data.mapper

import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.data.rates.CurrenciesResponse
import java.math.BigDecimal


class CurrencyCreatorImpl(
    private val urlCreator: UrlCreator = UrlCreator(),
    private val amountCalculator: AmountCalculator
) : CurrencyCreator {

    override fun createCurrency(
        response: CurrenciesResponse,
        names: Map<String, String>,
        baseAmount: BigDecimal
    ): List<Currency> {
        val baseCurrency = createBaseCurrency(response.base, names, baseAmount)
        val currencies = createOtherCurrencies(response, names, baseAmount)
        return listOf(baseCurrency).plus(currencies)
    }

    private fun createOtherCurrencies(
        response: CurrenciesResponse,
        names: Map<String, String>,
        baseAmount: BigDecimal
    ) =
        response.rates.map { entry ->
            createOtherCurrency(
                entry.key,
                entry.value,
                names,
                baseAmount
            )
        }

    private fun createBaseCurrency(
        symbol: String,
        names: Map<String, String>,
        amount: BigDecimal
    ): Currency {
        val symbol = symbol
        val url = urlCreator.createUrl(symbol)
        val countryName = names[symbol]
        return Currency(symbol, 1.toString(), amount, countryName, url)
    }

    private fun createOtherCurrency(
        symbol: String,
        rate: String,
        names: Map<String, String>,
        baseAmount: BigDecimal
    ): Currency {
        val url = urlCreator.createUrl(symbol)
        val countryName = names[symbol]
        return Currency(
            symbol,
            rate,
            amountCalculator.calculateAmount(rate, baseAmount),
            countryName,
            url
        )
    }
}
