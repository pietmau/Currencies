package com.pppp.currencies.presentation.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.pppp.currencies.data.pokos.Rate
import com.pppp.currencies.domain.usecases.GetRatesUseCase
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class RxRatesViewModel(private val useCase: GetRatesUseCase) : RatesViewModel, LifecycleObserver {
    private val data: MutableLiveData<List<Rate>> = MutableLiveData()
    private val subject: Subject<String> = BehaviorSubject.create()

    fun subscribe(lifecycle: Lifecycle, callback: (t: List<Rate>) -> Unit) {
        data.observe({ lifecycle }, callback)
        lifecycle.addObserver(this)
    }

    fun setBase(base: String) {
        subject.onNext(base)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun subscribe() {
        useCase.subscribe(base = subject, success = {
            data.value = it
        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun unsubscribe() {
        useCase.unSubscribe()
    }
}