package com.pppp.currencies.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.pppp.currencies.data.pokos.Currency
import java.math.BigDecimal

interface CurrenciesViewModel {

    val data: MutableLiveData<List<Currency>>

    fun changeBase(symbol: String, amount: BigDecimal)

    fun subscribe()

    fun unsubscribe()

    fun onBaseAmountChanged(baseSymbol: String, baseAmount: String)
}
