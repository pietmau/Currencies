package com.pppp.currencies.data.mapper

import com.pppp.currencies.data.pokos.Rate
import com.pppp.currencies.data.rates.RatesResponse


class RateCreatorImpl(private val urlCreator: UrlCreator = UrlCreator()) : RateCreator {
    override fun createBaseRate(response: RatesResponse): Rate {
        TODO()
    }

    override fun createRate(entry: Map.Entry<String, Double>, names: Map<String, String>): Rate {
        val symbol = entry.key
        val url = urlCreator.createUrl(symbol)
        val countryName = names[symbol]
        return Rate(symbol, entry.value, countryName, url)
    }
}
