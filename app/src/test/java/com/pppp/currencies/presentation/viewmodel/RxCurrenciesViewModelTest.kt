package com.pppp.currencies.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.data.repository.AmountCalculator
import com.pppp.currencies.domain.usecases.GetCurrenciesUseCase
import io.mockk.*
import io.reactivex.subjects.Subject
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal

private const val SYMBOL = "ITL"

@ExtendWith(InstantExecutorExtension::class)
internal class RxCurrenciesViewModelTest {
    private val useCase: GetCurrenciesUseCase = mockk(relaxed = true)
    private val amountCalculator: AmountCalculator = mockk()
    private lateinit var viewModel: RxCurrenciesViewModel
    private val subject: Subject<Pair<String, BigDecimal>> = mockk(relaxed = true)
    private val slot = slot<Pair<String, BigDecimal>>()
    private val succcess = slot<(List<Currency>) -> Unit>()
    private val liveData: MutableLiveData<List<Currency>> = mockk()
    private val currency: Currency = mockk()

    @BeforeEach
    fun setUp() {
        viewModel = RxCurrenciesViewModel(useCase, amountCalculator, subject, liveData)
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
        verify { subject.onNext(capture(slot)) }
        assertEquals(BigDecimal(baseAmount), slot.captured.second)
        assertEquals(SYMBOL, slot.captured.first)
        confirmVerified(subject)
    }

    @Test
    fun `when subscribes amount pushes to subject`() {
        val liveData = MutableLiveData<List<Currency>>()
        val viewModel = RxCurrenciesViewModel(useCase, amountCalculator, subject, liveData)
        // WHEN
        viewModel.subscribe()
        // THEN
        verify { useCase.subscribe(subject, capture(succcess), any()) }
        val list = listOf(currency)
        succcess.captured.invoke(list)
        assertEquals(list, liveData.value)
    }

    private fun changeBase(): String {
        every { amountCalculator.parseAmount(any()) } returns BigDecimal(1)
        val baseAmount = "1"
        viewModel.changeBaseAmount(SYMBOL, baseAmount)
        return baseAmount
    }
}