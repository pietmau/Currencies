package com.pppp.currencies.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.pppp.currencies.pokos.Currency
import java.math.BigDecimal

interface CurrenciesViewModel {

    val currencies: MutableLiveData<List<Currency>>

    val loading: MutableLiveData<Boolean>

    val errors: MutableLiveData<Throwable>

    fun changeBase(symbol: String, amount: BigDecimal)

    fun subscribe()

    fun unsubscribe()

    fun changeBaseAmount(baseSymbol: String, baseAmount: String)
}
