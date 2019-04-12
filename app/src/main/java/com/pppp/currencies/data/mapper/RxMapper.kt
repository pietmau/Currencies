package com.pppp.currencies.data.mapper

import com.pppp.currencies.data.pokos.Rate
import io.reactivex.Observable

interface RxMapper {

    fun getRates(base: String): Observable<List<Rate>>
}
