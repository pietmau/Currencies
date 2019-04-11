package com.pppp.currencies.data.mapper

import com.pppp.currencies.data.network.client.Client
import com.pppp.currencies.data.pokos.Rate


class RxMapperImpl(private val client: Client, private val urlCreator: UrlCreator) : RxMapper {

    override fun getRates(base: String) =
    // OKHttp is caching the names for us, therefore it is OK to call it every time
        client.getNames()
            .flatMap { names ->
                // Now we get the rates as a map
                client.getRates(base)
                    // We flatmap to get all the map entries one by one
                    .flatMapIterable { it.entries }
                    .map { entry -> createRate(entry, names) }
            }.toList()

    private fun createRate(entry: Map.Entry<String, Double>, names: Map<String, String>): Rate {
        val symbol = entry.key
        val url = urlCreator.createUrl(symbol)
        val countryName = names[symbol]
        return Rate(symbol, entry.value, countryName, url)
    }
}