package com.pppp.currencies.domain.usecases

import com.pppp.currencies.data.mapper.RxMapper
import com.pppp.currencies.data.pokos.Rate
import com.pppp.currencies.app.logger.Logger
import com.pppp.currencies.app.logger.LoggerImpl
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

// TODO retry on error!
/**
 * Emission does not happen on the main thread, because we start//TODO
 */
class RxGetRatesUseCase(
    private val mapper: RxMapper,
    private val mainScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val logger: Logger = LoggerImpl()
) :
    GetRatesUseCase {
    private var subscription: Disposable? = null
    private val TAG = RxGetRatesUseCase::class.simpleName

    override fun subscribe(
        // Gets the symbols from the user (if they change)
        base: Observable<String>, success: ((List<Rate>) -> Unit)?, failure: ((Throwable) -> Unit)?
    ) {
        subscription = base
            // In any case starts with EUR
            .startWith("EUR")
            // Every time the base change it restarts polling the rates
            .switchMap {
                logger.d(TAG, "New base! -> $it")
                getRates(it)
            }
            .observeOn(mainScheduler)
            .subscribe({
                success?.invoke(it)
            }, {
                failure?.invoke(it)
            })
    }

    private fun getRates(base: String) =
    // Implements backpressure management because it polls from the network very often,
    // and there will be errors, delays, etc
        Flowable.interval(1, TimeUnit.SECONDS)
            .onBackpressureDrop()
            .flatMap({
                mapper.getRates(base).toFlowable()
            }, 1)
            .toObservable()

    override fun unSubscribe() {
        subscription?.dispose()
    }

}

