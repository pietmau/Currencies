package com.pppp.currencies.domain.usecases

import com.pppp.currencies.data.pokos.Rate


interface GetRatesUseCase {
    fun subscribe(observer: (Rate) -> Unit)
    fun unSubscribe()
}