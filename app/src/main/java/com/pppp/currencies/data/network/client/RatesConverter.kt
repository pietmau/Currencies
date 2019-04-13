package com.pppp.currencies.data.network.client

import com.google.gson.reflect.TypeToken
import com.pppp.currencies.data.rates.CurrenciesResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

//TODO
class RatesConverter : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, Map<String, Double>?>? {
        val type = TypeToken.getParameterized(CurrenciesResponse::class.java, type).type
        val delegate: Converter<ResponseBody, CurrenciesResponse> =
            retrofit.nextResponseBodyConverter(this, type, annotations)
        TODO()
        //return Converter { value -> delegate.convert(value)?.rates }
    }
}
