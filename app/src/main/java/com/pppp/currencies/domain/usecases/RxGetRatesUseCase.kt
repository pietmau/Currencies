package com.pppp.currencies.domain.usecases

import com.pppp.currencies.data.mapper.RxMapper
import com.pppp.currencies.data.pokos.Currency
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.math.BigDecimal
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
        // Gets the currency from the user (if they change)
        baseCurrency: Observable<Pair<String, BigDecimal>>,
        success: ((List<Currency>) -> Unit)?,
        failure: ((Throwable) -> Unit)?
    ) {
        subscription = baseCurrency
            .startWith(Pair(DEFAULT_CURRENCY, BigDecimal(1)))
            // Every time the baseCurrency changes, it restarts polling the rates
            .switchMap { (baseSymbol, baseAmount) ->
                getRates(baseSymbol, baseAmount)
            }
            .observeOn(mainScheduler)
            .subscribe({
                success?.invoke(it)
            }, {
                failure?.invoke(it)
            })
    }

    private fun getRates(baseSymbol: String, baseAmount: BigDecimal) =
    // Implements back pressure management because it polls from the network very often
        Flowable.interval(1, TimeUnit.SECONDS)
            .flatMap({
                mapper.getRates(baseSymbol, baseAmount).toFlowable(BackpressureStrategy.LATEST)
                // Flatmaps only one subscription at the time
            }, 1)
            .toObservable()

    override fun unSubscribe() {
        subscription?.dispose()
    }

}

