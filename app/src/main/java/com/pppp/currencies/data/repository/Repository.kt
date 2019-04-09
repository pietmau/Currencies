package com.pppp.currencies.data.repository

import com.pppp.currencies.data.network.client.Client
import com.pppp.currencies.data.pokos.Rate
import io.reactivex.Observable
import io.reactivex.functions.BiFunction


class Repository(private val client: Client) {

    fun getRates(base: String): List<Rate> {
        val zz = client.getRatesObservable(base).flatMap { Observable.fromIterable(it.entries) }
        val aa = client.getNamesObservable()
        zz.zipWith(aa, object : BiFunction<String,String,String>{
            override fun apply(t1: String, t2: String): String {
                TODO("not implemented")
            }
        })
        TODO()
    }
}