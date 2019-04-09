package com.pppp.currencies.domain.usecases

import com.pppp.currencies.data.mapper.RxMapper
import com.pppp.currencies.data.pokos.Rate
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RxGetRatesUseCase(
    private val mapper: RxMapper ,
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val mainScheduler: Scheduler = AndroidSchedulers.mainThread()
) :
    GetRatesUseCase {
    private var subscription: Disposable? = null

    override fun subscribe(
        base: String,
        success: ((List<Rate>) -> Unit)?,
        failure: ((Throwable) -> Unit)?
    ) {
        subscription = Flowable.interval(1, TimeUnit.SECONDS)
            .onBackpressureDrop()
            .observeOn(ioScheduler)
            .flatMap({
                mapper.getRates(base).toFlowable()
            }, 1)
            .observeOn(mainScheduler)
            .subscribe({
                success?.invoke(it)
            }, {
                failure?.invoke(it)
            })
    }

    override fun unSubscribe() {
        subscription?.dispose()
    }

}

