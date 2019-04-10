package com.pppp.currencies.domain.usecases

import android.util.Log
import com.pppp.currencies.data.mapper.RxMapper
import com.pppp.currencies.data.pokos.Rate
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RxGetRatesUseCase(
    private val mapper: RxMapper,
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val mainScheduler: Scheduler = AndroidSchedulers.mainThread()
) :
    GetRatesUseCase {
    private var subscription: Disposable? = null

    override fun subscribe(
        base: Observable<String>,
        success: ((List<Rate>) -> Unit)?,
        failure: ((Throwable) -> Unit)?
    ) {
        subscription = base.switchMap {
            Log.e("foo", "switching = "+it)
            getRates(it) }
            .observeOn(mainScheduler)
            .subscribe({
                success?.invoke(it)
            }, {
                failure?.invoke(it)
            })
    }

    private fun getRates(base: String): Observable<List<Rate>> =
        Flowable.interval(1, TimeUnit.SECONDS)
            .onBackpressureDrop()
            .observeOn(ioScheduler)
            .flatMap({
                Log.e("foo", "base = "+base)
                mapper.getRates(base).toFlowable()
            }, 1)
            .toObservable()

    override fun unSubscribe() {
        subscription?.dispose()
    }

}

