package com.pppp.currencies.network.api

import retrofit2.http.GET
import retrofit2.http.Query


interface RatesApi {

    @GET("latest")
    suspend fun getRates(@Query("base") base: String): Map<String, Double>
}
