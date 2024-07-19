package com.ustinovauliana.currency.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import com.ustinovauliana.currency.api.models.ResponseDTO
import com.ustinovauliana.currency.api.utils.CurrencyApiKeyInterceptor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("latest")
    suspend fun getLatest(
        @Query("currencies") currency: String,
        @Query("base_currency") baseCurrency: String? = null,
    ) : ResponseDTO
}

fun CurrencyApi(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient? = null,
    json: Json = Json,
): CurrencyApi {
    return retrofit(baseUrl, apiKey, okHttpClient, json).create(CurrencyApi::class.java)
}


@OptIn(ExperimentalSerializationApi::class)
private fun retrofit(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient?,
    json: Json,
): Retrofit {

    val modifiedOkHttpClient: OkHttpClient = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
        .addInterceptor(CurrencyApiKeyInterceptor(apiKey))
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(modifiedOkHttpClient)
        .build()
}
