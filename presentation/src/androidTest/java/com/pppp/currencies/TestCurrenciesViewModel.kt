package com.pppp.currencies

import androidx.lifecycle.MutableLiveData
import com.pppp.currencies.pokos.Currency
import com.pppp.currencies.presentation.viewmodel.CurrenciesViewModel
import java.math.BigDecimal

class TestCurrenciesViewModel(
    override val currencies: MutableLiveData<List<Currency>>,
    override val loading: MutableLiveData<Boolean>,
    override val errors: MutableLiveData<Throwable> = MutableLiveData()
) :
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
