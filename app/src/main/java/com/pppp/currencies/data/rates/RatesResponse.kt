package com.pppp.currencies.data.rates

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RatesResponse(
    @SerializedName("base")
    @Expose
    var base: String? = null,
    @SerializedName("date")
    @Expose
    var date: String? = null,
    @SerializedName("rates")
    @Expose
    var rates: Map<String?, Double?>? = null
)