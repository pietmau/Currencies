package com.pppp.currencies.domain.usecases

import com.pppp.currencies.data.repository.Repository
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class RxGetCurrenciesUseCaseTest {
    private val repository: Repository = mockk()
    private val mainScheduler: Scheduler = Schedulers.trampoline()
    private val ioScheduler: Scheduler = Schedulers.trampoline()
    private val subscriptions: CompositeDisposable = mockk()
    private lateinit var usecase: RxGetCurrenciesUseCase

    @BeforeEach
    internal fun setUp() {
        usecase = RxGetCurrenciesUseCase(repository, mainScheduler, ioScheduler, subscriptions)
    }

    @Test
    fun `when success then success`() {
        // WHEN
        usecase.subscribe(Observable.just(Pair("EUR", BigDecimal(1))))
    }
}