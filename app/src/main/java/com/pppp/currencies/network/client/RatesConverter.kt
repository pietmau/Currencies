package com.pppp.currencies.network.client

import com.google.gson.reflect.TypeToken
import com.pppp.currencies.network.pokos.rates.RatesResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


class RatesConverter : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, Map<String, Double>?>? {
        val type = TypeToken.getParameterized(RatesResponse::class.java, type).type
        val delegate: Converter<ResponseBody, RatesResponse> =
            retrofit.nextResponseBodyConverter(this, type, annotations)
        return Converter { value -> delegate.convert(value)?.rates }
    }
}
