package com.pppp.currencies.domain.usecases

import com.pppp.currencies.data.pokos.Rate

class RxJavaGetRatesUseCase() : GetRatesUseCase {

    override fun subscribe(observer: (Rate) -> Unit) {

    }

    override fun unSubscribe() {}

}

