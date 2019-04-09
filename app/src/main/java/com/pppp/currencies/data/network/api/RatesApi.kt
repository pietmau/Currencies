package com.pppp.currencies.data.network.api

import com.pppp.currencies.data.rates.RatesResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {

    @GET("latest")
    suspend fun getRates(@Query("base") base: String): RatesResponse

    @GET("latest")
    fun getRatesObservable(@Query("base") base: String): Flowable<RatesResponse>
}
