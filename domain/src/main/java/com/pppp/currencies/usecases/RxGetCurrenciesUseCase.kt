package com.pppp.currencies.usecases

import com.pppp.currencies.Repository
import com.pppp.currencies.pokos.Currency
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.util.concurrent.TimeUnit


const val DEFAULT_CURRENCY = "EUR"

class RxGetCurrenciesUseCase(
    private val repository: Repository,
    private val mainScheduler: Scheduler,
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val computationScheduler: Scheduler = Schedulers.computation(),
    private val subscriptions: CompositeDisposable = CompositeDisposable(),
    numberOfAttempts: Int = 10,
    private val timeoutInSecs: Long = 3
) :
    GetCurrenciesUseCase {

    private val seconds: Observable<Long> =
        Observable.interval(1, TimeUnit.SECONDS, computationScheduler)

    private val function =
        BiFunction<Long?, Pair<String, BigDecimal>?, Pair<String, BigDecimal>> { _: Long, pair: Pair<String, BigDecimal> -> pair }

    private val numberOfAttempts = Observable.range(1, numberOfAttempts)

    override fun subscribe(
        baseCurrency: Observable<Pair<String, BigDecimal>>,
        success: ((List<Currency>) -> Unit)?,
        failure: ((Throwable) -> Unit)?
    ) {
        val currencies = baseCurrency.startWith(Pair(DEFAULT_CURRENCY, BigDecimal(1)))

        val subscription = seconds
            .withLatestFrom(currencies, function)
            .subscribeOn(ioScheduler)
            .flatMap { (baseSymbol, baseAmount) ->
                repository.getCurrencies(baseSymbol, baseAmount)
            }
            .timeout(timeoutInSecs, TimeUnit.SECONDS)
            .retryWhen { errors ->
                errors.zipWith(
                    numberOfAttempts,
                    BiFunction<Throwable, Int, Int> { error, attempt ->
                        failure?.invoke(createMessage(error, attempt))
                        attempt
                    }
                ).flatMap {
                    Observable.timer(
                        backOffTime(it).toLong(),
                        TimeUnit.SECONDS,
                        computationScheduler
                    )
                }
            }
            .observeOn(mainScheduler)
            .subscribe({
                success?.invoke(it)
            }, {
                failure?.invoke(it)
            })
        subscriptions.add(subscription)
    }

    override fun unSubscribe() {
        subscriptions.clear()
    }

    private fun createMessage(error: Throwable, attempt: Int) =
        Exception(
            "ERROR: ${error.localizedMessage} Will retry in ${backOffTime(attempt)} seconds"
        )

    private fun backOffTime(attempt: Int) = Math.pow(2.toDouble(), attempt.toDouble()).toLong()
}



