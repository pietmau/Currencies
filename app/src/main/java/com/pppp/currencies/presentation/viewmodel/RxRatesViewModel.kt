package com.pppp.currencies.presentation.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pppp.currencies.data.pokos.Rate
import com.pppp.currencies.domain.usecases.GetRatesUseCase
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

//TODO careful with memory leaks!!!
// TODO implemnet retry!!!
class RxRatesViewModel(private val useCase: GetRatesUseCase) : ViewModel(), RatesViewModel,
    LifecycleObserver {
    override val data: MutableLiveData<List<Rate>> = MutableLiveData()
    private val subject: Subject<String> = BehaviorSubject.create()

    override fun changeBase(base: String) {
        subject.onNext(base)
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