package com.ustinovauliana.currencyconverterapp

import android.content.Context
import com.ustinovauliana.currency.api.CurrencyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCurrencyApi(okHttpClient: OkHttpClient?): CurrencyApi {
        return CurrencyApi(
            baseUrl = BuildConfig.CURRENCY_API_BASE_URL,
            apiKey = BuildConfig.CURRENCY_API_KEY,
            okHttpClient = okHttpClient,
        )
    }

}
