package com.pppp.currencies.network.client

import com.pppp.currencies.network.api.RatesApi
import com.pppp.currencies.network.pokos.rates.RatesResponse
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


class RetrofitClient(ratesBaseUrl: String = "https://revolut.duckdns.org", cacheDir: File) :
    Client {
    private val interceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    private val cache = Cache(cacheDir, 10 * 1024 * 1024)
    private val client = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(interceptor)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(ratesBaseUrl)
        .client(client)
        .addConverterFactory(RatesConverter())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val ratesApi = retrofit.create(RatesApi::class.java)

    override suspend fun getRates(base: String): Map<String, Double> = ratesApi.getRates(base)

    override suspend fun getNames(): RatesResponse {
        TODO("not implemented")
    }
}