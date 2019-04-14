package com.pppp.currencies.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pppp.currencies.pokos.Currency
import com.pppp.data.data.repository.AmountCalculator
import com.pppp.currencies.usecases.GetCurrenciesUseCase
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.math.BigDecimal

class RxCurrenciesViewModel(
    private val currenciesUseCase: GetCurrenciesUseCase,
    private val amountCalculator: com.pppp.data.data.repository.AmountCalculator = com.pppp.data.data.repository.AmountCalculator(),
    private val subject: Subject<Pair<String, BigDecimal>> = BehaviorSubject.create(),
    override val currencies: MutableLiveData<List<Currency>> = MutableLiveData(),
    override val loading: MutableLiveData<Boolean> = MutableLiveData(),
    override val errors: MutableLiveData<Throwable> = MutableLiveData()
) :
    CurrenciesViewModel,
    ViewModel() {

    override fun changeBase(baseSymbol: String, baseAmount: BigDecimal) {
        subject.onNext(Pair(baseSymbol, baseAmount))
    }

    override fun changeBaseAmount(baseSymbol: String, baseAmount: String) {
        amountCalculator.parseAmount(baseAmount)?.let { changeBase(baseSymbol, it) }
    }

    override fun subscribe() {
        currenciesUseCase.subscribe(
            base = subject,
            success = ::emitCurrenciesList,
            failure = errors::postValue
        )
    }

    private fun emitCurrenciesList(list: List<Currency>) {
        currencies.postValue(list)
        loading.postValue(false)
    }

    override fun unsubscribe() {
        currenciesUseCase.unSubscribe()
        loading.postValue(false)
    }
}