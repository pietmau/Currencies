package com.pppp.currencies

import androidx.lifecycle.MutableLiveData
import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.presentation.viewmodel.CurrenciesViewModel
import java.math.BigDecimal

class TestCurrenciesViewModel(override val data: MutableLiveData<List<Currency>>) :
    CurrenciesViewModel {

    override fun changeBase(symbol: String, amount: BigDecimal) {
    }

    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

    override fun changeBaseAmount(baseSymbol: String, baseAmount: String) {
    }

}
