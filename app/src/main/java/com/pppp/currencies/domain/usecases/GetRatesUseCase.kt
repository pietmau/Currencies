package com.pppp.currencies.domain.usecases

import com.pppp.currencies.data.pokos.Rate


interface GetRatesUseCase {

    fun subscribe(
        base: String,
        success: ((List<Rate>) -> Unit)? = null,
        failure: ((Throwable) -> Unit)? = null
    )

    fun unSubscribe()

}