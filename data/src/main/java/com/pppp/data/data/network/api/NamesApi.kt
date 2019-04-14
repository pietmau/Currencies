package com.pppp.data.data.network.api

import io.reactivex.Flowable
import retrofit2.http.GET

interface NamesApi {

    @GET("api/currencies.json")
    suspend fun getNames(): Map<String, String>

    @GET("api/currencies.json")
    fun getNamesObservable(): Flowable<Map<String, String>>
}
