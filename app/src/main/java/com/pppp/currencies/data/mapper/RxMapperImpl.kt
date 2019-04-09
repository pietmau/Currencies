package com.pppp.currencies.data.mapper

import com.pppp.currencies.data.network.client.Client
import com.pppp.currencies.data.pokos.Rate
import io.reactivex.Observable
import io.reactivex.Single

class RxMapperImpl(private val client: Client) : RxMapper {

    override fun getRates(base: String): Single<List<Rate>> {
        val rates = client.getRatesObservable(base)
            .flatMap { Observable.fromIterable(it.entries) }
        // OKHttp is caching the responses
        return client.getNamesObservable().flatMap { names ->
            rates.map { entry ->
                val symbol = entry.key
                Rate(symbol, entry.value, names[symbol])
            }
        }.toList()
    }
}
