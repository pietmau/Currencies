package com.pppp.currencies.data.pokos

import java.math.BigDecimal


data class Currency(
    val symbol: String,
    val rate: String,//TODO use big decimal instead
    val amount: BigDecimal = BigDecimal(1),
    val country: String?,
    val url: String
)