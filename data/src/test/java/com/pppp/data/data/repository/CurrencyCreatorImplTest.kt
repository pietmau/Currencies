package com.pppp.data.data.repository

import com.pppp.currencies.pokos.Currency
import com.pppp.data.data.rates.CurrenciesResponse
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal

private const val URL = "www"
private const val EUR = "EUR"
private const val POUND = "pound"
private const val EURO = "euro"
private const val GBP = "GBP"
private val USD = "USD"
private val DOLLAR = "dollar"

@ExtendWith(MockKExtension::class)
internal class CurrencyCreatorImplTest {
    @RelaxedMockK
    lateinit var urlCreator: UrlCreator
    @RelaxedMockK
    lateinit var amountCalculator: AmountCalculator
    @InjectMockKs
    lateinit var currencyCreator: CurrencyCreatorImpl

    @BeforeEach
    fun setUp() {
        every { urlCreator.createUrl(any()) } returns URL
        every { amountCalculator.calculateAmount(any(),any()) } returns BigDecimal(2)
    }

    @Test
    fun when_bar_then_fobar() {
        // GIVEN
        val response = makeResponse(makeRates())
        // WHEN
        val result = currencyCreator.createCurrency(response, makeNames(), BigDecimal(1))
        // THEN
        assertEquals(Currency(EUR, "1", BigDecimal(1), EURO, URL), result[0])
        assertEquals(Currency(GBP, "1.5", BigDecimal(2), POUND, URL), result[1])
        assertEquals(Currency(USD, "0.9", BigDecimal(2), DOLLAR, URL), result[2])
    }

    private fun makeResponse(rates: Map<String, String>) =
        CurrenciesResponse(EUR, "", rates)

    private fun makeNames() =
        mapOf<String, String>(Pair(EUR, EURO), Pair(GBP, POUND), Pair(USD, DOLLAR))

    private fun makeRates() = mapOf<String, String>(Pair(GBP, "1.5"), Pair(USD, "0.9"))
}