package com.pppp.data.data

import com.pppp.currencies.Repository
import com.pppp.currencies.pokos.Currency
import com.pppp.data.data.network.client.Client
import com.pppp.data.data.repository.CurrencyCreator
import io.reactivex.Observable
import java.math.BigDecimal

class RetrofitRepository(private val client: Client, private val currencyCreator: CurrencyCreator) :
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
