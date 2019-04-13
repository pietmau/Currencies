package com.pppp.currencies.domain.usecases

import com.pppp.currencies.data.pokos.Currency
import io.reactivex.Observable
import java.math.BigDecimal


interface GetCurrenciesUseCase {

    fun unSubscribe()

    fun subscribe(
        base: Observable<Pair<String, BigDecimal>>,
        success: ((List<Currency>) -> Unit)? = null,
        failure: ((Throwable) -> Unit)? = null
    )
}