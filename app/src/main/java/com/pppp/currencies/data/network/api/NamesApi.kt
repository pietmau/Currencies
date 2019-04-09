package com.pppp.currencies.data.network.api

import io.reactivex.Observable
import retrofit2.http.GET

interface NamesApi {

    @GET("api/currencies.json")
    suspend fun getNames(): Map<String?, String?>

    @GET("api/currencies.json")
    fun getNamesObservable(): Observable<Map<String?, String?>>
}
