package com.pppp.currencies.pokos

import java.math.BigDecimal


data class Currency(
    val symbol: String,
    val rate: String,
    val amount: BigDecimal = BigDecimal(1),
    val country: String?,
    val url: String
)
