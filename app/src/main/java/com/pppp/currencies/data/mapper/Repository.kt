package com.pppp.currencies.data.mapper

import com.pppp.currencies.data.pokos.Currency
import io.reactivex.Observable
import java.math.BigDecimal

/**
 * Gets the response from the api and maps it.
 */
interface Repository {

    fun getCurrencies(baseSymbol: String, basAmount: BigDecimal): Observable<List<Currency>>
}
