package com.pppp.currencies.domain.usecases

import com.pppp.currencies.data.mapper.RxMapper
import com.pppp.currencies.data.pokos.Rate
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

// TODO retry on error!

const val DEFAULT_CURRENCY = "EUR"

/**
 * Emission does not happen on the main thread, because we start//TODO
 */
class RxGetRatesUseCase(
    private val mapper: RxMapper,
    private val mainScheduler: Scheduler = AndroidSchedulers.mainThread()
) :
    GetRatesUseCase {
    private var subscription: Disposable? = null

    override fun subscribe(
        // Gets the symbols from the user (if they change)
        base: Observable<String>, success: ((List<Rate>) -> Unit)?, failure: ((Throwable) -> Unit)?
    ) {
        subscription = base
            // In any case we start with the default
            .startWith(DEFAULT_CURRENCY)
            // Every time the base changes it restarts polling the rates
            .switchMap { getRates(it) }
            .observeOn(mainScheduler)
            .subscribe({
                success?.invoke(it)
            }, {
                failure?.invoke(it)
            })
    }

    private fun getRates(base: String) =
    // Implements back pressure management because it polls from the network very often
        Flowable.interval(1, TimeUnit.SECONDS)
            // If we are too busy we drop all except the latest
            .onBackpressureLatest()
            .flatMap({
                mapper.getRates(base).toFlowable()
                // Only one subscription at the time
            }, 1)
            .toObservable()

    override fun unSubscribe() {
        subscription?.dispose()
    }

}

