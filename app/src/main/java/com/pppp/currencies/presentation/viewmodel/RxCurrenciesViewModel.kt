package com.pppp.currencies.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pppp.currencies.data.mapper.AmountCalculator
import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.domain.usecases.GetCurrenciesUseCase
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.math.BigDecimal

//TODO careful with memory leaks!!!
// TODO implemnet retry!!!
class RxCurrenciesViewModel(
    private val currenciesUseCase: GetCurrenciesUseCase,
    private val amountCalculator: AmountCalculator = AmountCalculator()
) :
    CurrenciesViewModel,
    ViewModel() {
    override val data: MutableLiveData<List<Currency>> = MutableLiveData()
    private val subject: Subject<Pair<String, BigDecimal>> = BehaviorSubject.create()

    override fun changeBase(baseSymbol: String, baseAmount: BigDecimal) {
        subject.onNext(Pair(baseSymbol, baseAmount))
    }

    override fun onBaseAmountChanged(baseSymbol: String, baseAmount: String) {
        amountCalculator.parseAmount(baseAmount)
            ?.let { changeBase(baseSymbol, it) }
    }

    override fun subscribe() {
        currenciesUseCase.subscribe(base = subject, success = {
            data.value = it
        })
    }

    override fun unsubscribe() {
        currenciesUseCase.unSubscribe()
    }
}