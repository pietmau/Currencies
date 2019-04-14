package com.pppp.currencies.domain.usecases

import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.data.repository.Repository
import io.mockk.*
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
    private val subscriptions: CompositeDisposable = mockk(relaxed = true)
    private lateinit var usecase: RxGetCurrenciesUseCase
    private val success: (List<Currency>) -> Unit = mockk(relaxed = true)
    private val failure: (Throwable) -> Unit = mockk(relaxed = true)
    private val slot = slot<Throwable>()

    @BeforeEach
    internal fun setUp() {
        usecase = RxGetCurrenciesUseCase(repository, mainScheduler, ioScheduler, subscriptions)
        every { success(any()) } just Runs
    }

    @Test
    fun `when subscribes then subscribes`() {
        // GIVEN
        subscribe()
        // THEN
        verify { subscriptions.add(any()) }
        confirmVerified(subscriptions)
    }

    @Test
    fun `when success then success`() {
        // GIVEN
        val emptyList = subscribe()
        // THEN
        // unfortunately we must do this, because the emission is every 1 second
        Thread.sleep(1100)
        verify { success(emptyList) }
        confirmVerified(success)
    }

    @Test
    fun `emits multiple times`() {
        // GIVEN
        val emptyList = subscribe()
        // THEN
        // unfortunately we must do this, because the emission is every 1 second
        Thread.sleep(2100)
        verify(atLeast = 2) { success(emptyList) }
        confirmVerified(success)
    }

    @Test
    fun `when failure then failure`() {
        // GIVEN
        val exception = Exception()
        every { repository.getCurrencies(any(), any()) } throws exception
        // WHEN
        usecase.subscribe(Observable.just(Pair("GBP", BigDecimal(1))), failure = failure)
        // THEN
        // unfortunately we must do this, because the emission is every 1 second
        Thread.sleep(1100)
        verify { failure(ofType<Exception>()) }
        confirmVerified(failure)
    }

    @Test
    fun `when unsubscribes then unsubscribes`() {
        // GIVEN
        usecase.unSubscribe()
        // THEN
        verify { subscriptions.clear() }
        confirmVerified(subscriptions)
    }


    private fun subscribe(): List<Currency> {
        val emptyList = emptyList<Currency>()
        every { repository.getCurrencies(any(), any()) } returns Observable.just(emptyList)
        // WHEN
        usecase.subscribe(Observable.just(Pair("GBP", BigDecimal(1))), success)
        return emptyList
    }
}