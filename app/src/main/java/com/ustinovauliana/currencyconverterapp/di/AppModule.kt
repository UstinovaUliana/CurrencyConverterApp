package com.ustinovauliana.currencyconverterapp.di
import com.ustinovauliana.currencyconverterapp.data.api.CurrencyApi
import com.ustinovauliana.currencyconverterapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyApi(okHttpClient: OkHttpClient): CurrencyApi {
        return CurrencyApi(
            baseUrl = BuildConfig.CURRENCY_API_BASE_URL,
            apiKey = BuildConfig.CURRENCY_API_KEY,
            okHttpClient = okHttpClient,
        )

    }
}
