package com.pppp.currencies.data.pokos


data class Rate(
    val symbol: String,
    val rate: Double,//TODO use big decimal instead
    val country: String?,
    val url: String,
    val isBase: Boolean = false
)