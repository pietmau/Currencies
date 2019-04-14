package com.pppp.currencies.usecases

import com.pppp.currencies.Repository
import com.pppp.currencies.pokos.Currency
import io.mockk.*
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicInteger

private const val GBP = "GBP"

internal class RxGetCurrenciesUseCaseTest {
    private val repository: Repository = mockk()
    private val mainScheduler: Scheduler = Schedulers.trampoline()
    private val subscriptions: CompositeDisposable = mockk(relaxed = true)
    private lateinit var usecase: RxGetCurrenciesUseCase
    private val success: (List<Currency>) -> Unit = mockk(relaxed = true)
    private val failure: (Throwable) -> Unit = mockk(relaxed = true)

    @BeforeEach
    internal fun setUp() {
        usecase = RxGetCurrenciesUseCase(
            repository,
            mainScheduler,
            mainScheduler,
            subscriptions,
            numberOfAttempts = 2,
            seconds = Observable.range(1, 4).map { it.toLong() },
            timer = object : CurrenciesTimer() {
                override fun run(time: Long) = Observable.range(1, 4).map { it.toLong() }
            }
        )
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
        verify { success(emptyList) }
        confirmVerified(success)
    }

    @Test
    fun `emits multiple times`() {
        // GIVEN
        val emptyList = subscribe()
        // THEN
        verify(atLeast = 2) { success(emptyList) }
        confirmVerified(success)
    }

    @Test
    fun `when failure then failure`() {
        // GIVEN
        throwException()
        // WHEN
        usecase.subscribe(Observable.just(Pair(GBP, BigDecimal(1))), failure = failure)
        // THEN
        verify { failure(any()) }
        confirmVerified(failure)
    }

    @Test
    fun `when error then retries`() {
        // GIVEN
        throwException()
        // WHEN
        usecase.subscribe(Observable.just(Pair(GBP, BigDecimal(1))))
        // THEN
        verify(exactly = 2) { repository.getCurrencies(any(), any()) }
        confirmVerified(repository)
    }

    @Test
    fun `when slow then times out`() {
        // GIVEN
        val repo = SlowRepositorySpy()
        val usecase = RxGetCurrenciesUseCase(
            repo,
            mainScheduler,
            mainScheduler,
            subscriptions,
            numberOfAttempts = 2,
            seconds = Observable.range(1, 4).map { it.toLong() },
            timer = object : CurrenciesTimer() {
                override fun run(time: Long) = Observable.range(1, 4).map { it.toLong() }
            }
        )
        // WHEN
        usecase.subscribe(Observable.just(Pair(GBP, BigDecimal(1))))
        // THEN
        assertEquals(2, repo.count.get())
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
        usecase.subscribe(Observable.just(Pair(GBP, BigDecimal(1))), success)
        return emptyList
    }

    private fun throwException() {
        val exception = Exception()
        every { repository.getCurrencies(any(), any()) } throws exception
    }
}

class SlowRepositorySpy : Repository {
    val count = AtomicInteger(0)

    override fun getCurrencies(
        baseSymbol: String,
        basAmount: BigDecimal
    ): Observable<List<Currency>> {
        count.incrementAndGet()
        try {
            Thread.sleep(3100)
        } catch (exception: Exception) {
        }
        return Observable.just(emptyList())
    }
}