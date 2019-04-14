package com.pppp.currencies.domain.usecases

import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.data.repository.Repository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

const val DEFAULT_CURRENCY = "EUR"

class RxGetCurrenciesUseCase(
    private val repository: Repository,
    private val mainScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val subscriptions: CompositeDisposable = CompositeDisposable()
) :
    GetCurrenciesUseCase {

    override fun subscribe(
        baseCurrency: Observable<Pair<String, BigDecimal>>,
        success: ((List<Currency>) -> Unit)?,
        failure: ((Throwable) -> Unit)?
    ) {
        val currencies = baseCurrency.startWith(Pair(DEFAULT_CURRENCY, BigDecimal(1)))
        val seconds = Observable.interval(1, TimeUnit.SECONDS)
        val subscription = seconds
            .withLatestFrom(currencies, biFunction)
            .subscribeOn(ioScheduler)
            .flatMap { (baseSymbol, baseAmount) ->
                repository.getCurrencies(baseSymbol, baseAmount)
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

    private val biFunction =
        BiFunction<Long?, Pair<String, BigDecimal>?, Pair<String, BigDecimal>> { _: Long, pair: Pair<String, BigDecimal> -> pair }
}

