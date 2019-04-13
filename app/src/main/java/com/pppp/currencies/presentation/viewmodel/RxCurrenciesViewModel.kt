package com.pppp.currencies.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.data.repository.AmountCalculator
import com.pppp.currencies.domain.usecases.GetCurrenciesUseCase
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.math.BigDecimal

class RxCurrenciesViewModel(
    private val currenciesUseCase: GetCurrenciesUseCase,
    private val amountCalculator: AmountCalculator = AmountCalculator(),
    private val subject: Subject<Pair<String, BigDecimal>> = BehaviorSubject.create(),
    override val data: MutableLiveData<List<Currency>> = MutableLiveData()
) :
    CurrenciesViewModel,
    ViewModel() {

    override fun changeBase(baseSymbol: String, baseAmount: BigDecimal) {
        subject.onNext(Pair(baseSymbol, baseAmount))
    }

    override fun changeBaseAmount(baseSymbol: String, baseAmount: String) {
        amountCalculator.parseAmount(baseAmount)
            ?.let { changeBase(baseSymbol, it) }
    }

    override fun subscribe() {
        currenciesUseCase.subscribe(base = subject, success = {
            data.postValue(it)
        })
    }

    override fun unsubscribe() {
        currenciesUseCase.unSubscribe()
    }
}