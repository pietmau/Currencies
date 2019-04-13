package com.pppp.currencies.data.repository

import com.pppp.currencies.data.network.client.Client
import com.pppp.currencies.data.pokos.Currency
import io.reactivex.Observable
import java.math.BigDecimal

class RepositoryImpl(private val client: Client, private val currencyCreator: CurrencyCreator) :
    Repository {

    override fun getCurrencies(
        baseSymbol: String,
        baseAmount: BigDecimal
    ): Observable<List<Currency>> {
        // OKHttp is caching the names for us, therefore it is OK to call it every time
        return client.getNames()
            .flatMap { names ->
                client.getRates(baseSymbol)
                    .map { response ->
                        currencyCreator.createCurrency(response, names, baseAmount)
                    }
            }
    }
}
