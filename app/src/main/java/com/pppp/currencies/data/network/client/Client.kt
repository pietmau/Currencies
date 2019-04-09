package com.pppp.currencies.data.network.client

import io.reactivex.Observable

interface Client {

    suspend fun getRates(base: String): Map<String?, Double?>

    suspend fun getNames(): Map<String?, String?>

    fun getRatesObservable(base: String): Observable<Map<String?, Double?>>

    fun getNamesObservable(): Observable<Map<String?, String?>>

}