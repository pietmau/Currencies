package com.pppp.currencies.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.data.repository.AmountCalculator
import com.pppp.currencies.domain.usecases.GetCurrenciesUseCase
import io.mockk.*
import io.reactivex.subjects.Subject
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal

private const val SYMBOL = "ITL"

@ExtendWith(InstantExecutorExtension::class)
internal class RxCurrenciesViewModelTest {
    private val useCase: GetCurrenciesUseCase = mockk(relaxed = true)
    private val amountCalculator: AmountCalculator = mockk()
    private lateinit var viewModel: RxCurrenciesViewModel
    private val subject: Subject<Pair<String, BigDecimal>> = mockk(relaxed = true)
    private val pair = slot<Pair<String, BigDecimal>>()
    private val succcess = slot<(List<Currency>) -> Unit>()
    private val currencies: MutableLiveData<List<Currency>> = mockk(relaxed = true)
    private val loading: MutableLiveData<Boolean> = mockk(relaxed = true)
    private val currency: Currency = mockk()
    private val errors: MutableLiveData<Throwable> = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        viewModel =
            RxCurrenciesViewModel(useCase, amountCalculator, subject, currencies, loading, errors)
    }

    @Test
    fun `when change amount parses amount`() {
        // WHEN
        val baseAmount = changeBase()
        // THEN
        verify { amountCalculator.parseAmount(baseAmount) }
        confirmVerified(amountCalculator)
    }

    @Test
    fun `when change amount pushes to subject`() {
        // WHEN
        val baseAmount = changeBase()
        // THEN
        verify { subject.onNext(capture(pair)) }
        assertEquals(BigDecimal(baseAmount), pair.captured.second)
        assertEquals(SYMBOL, pair.captured.first)
        confirmVerified(subject)
    }

    @Test
    fun `when gets data pushes to live data`() {
        val liveData = MutableLiveData<List<Currency>>()
        val viewModel = RxCurrenciesViewModel(useCase, amountCalculator, subject, liveData)
        // WHEN
        val list = getsData(viewModel)
        // THEN
        assertEquals(list, liveData.value)
    }

    @Test
    fun `when unsubscribes then hides progress`() {
        // WHEN
        viewModel.unsubscribe()
        // THEN
        verify { loading.postValue(false) }
    }

    @Test
    fun `when gets data then hides progress`() {
        // WHEN
        getsData(viewModel)
        // THEN
        verify { loading.postValue(false) }
    }

    @Test
    fun `when unsubscribe then unsubscribes`() {
        // WHEN
        viewModel.unsubscribe()
        // THEN
        verify { useCase.unSubscribe() }
    }

    @Test
    fun `when errors then shows error`() {
        // WHEN
        getsData(viewModel)
        // THEN
        verify { loading.postValue(false) }
    }


    private fun changeBase(): String {
        every { amountCalculator.parseAmount(any()) } returns BigDecimal(1)
        val baseAmount = "1"
        viewModel.changeBaseAmount(SYMBOL, baseAmount)
        return baseAmount
    }

    private fun getsData(viewModel: RxCurrenciesViewModel): List<Currency> {
        viewModel.subscribe()
        verify { useCase.subscribe(subject, capture(succcess), any()) }
        val list = listOf(currency)
        succcess.captured.invoke(list)
        return list
    }
}
