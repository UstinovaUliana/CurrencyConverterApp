package com.ustinovauliana.currencyconverterapp.data.api

import com.ustinovauliana.currencyconverterapp.data.api.models.ResponseDTO
import com.ustinovauliana.currencyconverterapp.data.api.utils.retrofit
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("latest")
    suspend fun getLatest(
        @Query("currencies") currency: String,
        @Query("base_currency") baseCurrency: String? = null,
    ): ResponseDTO
}

fun CurrencyApi(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient? = null,
    json: Json = Json,
): CurrencyApi {
    return retrofit(baseUrl, apiKey, okHttpClient, json).create(CurrencyApi::class.java)
}
