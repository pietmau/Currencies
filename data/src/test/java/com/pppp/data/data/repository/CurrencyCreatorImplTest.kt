package com.pppp.data.data.repository

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class CurrencyCreatorImplTest {
    @MockK
    lateinit var urlCreator: UrlCreator
    @MockK
    lateinit var amountCalculator: AmountCalculator
    @InjectMockKs
    lateinit var currencyCreator: CurrencyCreatorImpl


}