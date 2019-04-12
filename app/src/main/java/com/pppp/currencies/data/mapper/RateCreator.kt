package com.pppp.currencies.data.mapper

import com.pppp.currencies.data.pokos.Rate
import com.pppp.currencies.data.rates.RatesResponse

interface RateCreator {

    fun createRate(entry: Map.Entry<String, Double>, names: Map<String, String>): Rate

    fun createBaseRate(response: RatesResponse): Rate
}
