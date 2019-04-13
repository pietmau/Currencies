package com.pppp.currencies.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.domain.usecases.GetRatesUseCase
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.math.BigDecimal

//TODO careful with memory leaks!!!
// TODO implemnet retry!!!
class RxCurrenciesViewModel(private val useCase: GetRatesUseCase) : ViewModel(), CurrenciesViewModel {
    override val data: MutableLiveData<List<Currency>> = MutableLiveData()
    private val subject: Subject<Pair<String, BigDecimal>> = BehaviorSubject.create()

    override fun changeBase(baseSymbol: String, baseAmount: BigDecimal) {
        subject.onNext(Pair(baseSymbol, baseAmount))
    }

    override fun subscribe() {
        useCase.subscribe(base = subject, success = {
            data.value = it
        })
    }

    override fun unsubscribe() {
        useCase.unSubscribe()
    }
}