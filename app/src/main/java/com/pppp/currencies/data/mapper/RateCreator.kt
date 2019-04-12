package com.pppp.currencies.data.mapper

import com.pppp.currencies.data.pokos.Rate
import com.pppp.currencies.data.rates.RatesResponse

interface RateCreator {

    fun createRates(response: RatesResponse, names: Map<String, String>): List<Rate>
}
