package com.ustinovauliana.currencyconverterapp.data.api.utils

import okhttp3.Interceptor
import okhttp3.Response

internal class CurrencyApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("apikey", apiKey)
                .build()
        )
    }
}

