package com.pppp.currencies.domain.usecases

import com.pppp.currencies.data.pokos.Rate
import io.reactivex.Observable


interface GetRatesUseCase {

    fun unSubscribe()

    fun subscribe(
        base: Observable<String>,
        success: ((List<Rate>) -> Unit)? = null,
        failure: ((Throwable) -> Unit)? = null
    )
}