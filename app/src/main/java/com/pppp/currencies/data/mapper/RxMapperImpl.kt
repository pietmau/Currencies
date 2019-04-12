package com.pppp.currencies.data.mapper

import com.pppp.currencies.data.network.client.Client
import com.pppp.currencies.data.pokos.Rate
import io.reactivex.Observable


class RxMapperImpl(private val client: Client, private val rateCreator: RateCreator) : RxMapper {

    override fun getRates(base: String): Observable<List<Rate>> {
        // OKHttp is caching the names for us, therefore it is OK to call it every time
        return client.getNames()
            .flatMap { names ->
                // Now we get the rates as a map
                client.getRates(base)
                    // We flatmap to get all the map entries one by one
                    .map { response ->
                        // We must not forget the base currency
                        rateCreator.createRates(response, names)
                    }

            }
    }
}