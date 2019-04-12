package com.pppp.currencies.data.mapper

import com.pppp.currencies.data.pokos.Rate
import com.pppp.currencies.data.rates.RatesResponse


class RateCreatorImpl(private val urlCreator: UrlCreator = UrlCreator()) : RateCreator {

    override fun createRates(response: RatesResponse, names: Map<String, String>) =
    // The base rate is the first one
        listOf(createBaseRate(response, names))
            .plus(response.rates.map { entry -> createRate(entry, names) })

    fun createBaseRate(response: RatesResponse, names: Map<String, String>): Rate {
        val symbol = response.base
        val url = urlCreator.createUrl(symbol)
        val countryName = names[symbol]
        return Rate(symbol, 1.0, countryName, url, true)
    }

    fun createRate(entry: Map.Entry<String, Double>, names: Map<String, String>): Rate {
        val symbol = entry.key
        val url = urlCreator.createUrl(symbol)
        val countryName = names[symbol]
        return Rate(symbol, entry.value, countryName, url)
    }
}
