package com.pppp.currencies.network.client

import com.pppp.currencies.network.pokos.rates.RatesResponse

interface Client {

    suspend fun getRates(base: String): Map<String, Double>

    suspend fun getNames(): RatesResponse//TODO wrong
}