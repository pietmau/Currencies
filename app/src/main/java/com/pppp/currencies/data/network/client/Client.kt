package com.pppp.currencies.data.network.client

import com.pppp.currencies.data.rates.RatesResponse
import io.reactivex.Observable

interface Client {

    suspend fun getRatesXXX(base: String): Map<String, Double>

    suspend fun getNamesXXX(): Map<String, String>

    fun getRates(base: String): Observable<RatesResponse>

    fun getNames(): Observable<Map<String, String>>

}