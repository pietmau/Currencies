package com.pppp.currencies.data.rates

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RatesResponse(
    @SerializedName("base")
    @Expose
    var base: String = "",
    @SerializedName("date")
    @Expose
    var date: String = "",
    @SerializedName("rates")
    @Expose
    var rates: Map<String, Double> = emptyMap()
)