package com.pppp.currencies.presentation.viewmodel

import com.pppp.currencies.data.repository.AmountCalculator
import com.pppp.currencies.domain.usecases.GetCurrenciesUseCase
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal

private const val SYMBOL = "ITL"

@ExtendWith(MockKExtension::class)
internal class RxCurrenciesViewModelTest {
    @MockK
    private lateinit var currenciesUseCase: GetCurrenciesUseCase
    @MockK
    private lateinit var amountCalculator: AmountCalculator
    @InjectMockKs
    private lateinit var viewModel: RxCurrenciesViewModel

    @Before
    fun setUp() {

    }

    @Test
    fun `when change amount changes base`() {
        // GIVEN
        every { amountCalculator.parseAmount(any()) } returns BigDecimal(1)
        // WHEN
        val baseAmount = "1.0"
        viewModel.changeBaseAmount(SYMBOL, baseAmount)
        // THEN
        verify { amountCalculator.parseAmount(baseAmount) }
        verify {  }
    }
}