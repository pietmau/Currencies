package com.pppp.currencies.data.mapper

import com.pppp.currencies.data.pokos.Rate
import io.reactivex.Single

interface RxMapper {

    fun getRates(base: String): Single<List<Rate>>
}
