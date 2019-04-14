package com.pppp.currencies.data.repository

class UrlCreator(private val baseUrl: String = "https://www.countryflags.io/%s/flat/64.png") {

    fun createUrl(symbol: String): String {
        val countryCode = symbol.toLowerCase().dropLast(1)
        return String.format(baseUrl, countryCode)
    }
}
