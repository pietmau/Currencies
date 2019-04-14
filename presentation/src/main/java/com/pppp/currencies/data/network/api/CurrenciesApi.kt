package com.pppp.currencies.data.network.api

import com.pppp.currencies.data.rates.CurrenciesResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesApi {

    @GET("latest")
    suspend fun getCurrencies(@Query("base") base: String): CurrenciesResponse

    @GET("latest")
    fun getCurrenciesObservable(@Query("base") base: String): Flowable<CurrenciesResponse>
}
