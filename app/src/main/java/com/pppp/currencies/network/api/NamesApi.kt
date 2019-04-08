package com.pppp.currencies.network.api

import com.pppp.currencies.network.pokos.rates.RatesResponse
import retrofit2.http.GET


interface NamesApi {

    @GET("currencies.json")
    suspend fun getNames(): RatesResponse
}
