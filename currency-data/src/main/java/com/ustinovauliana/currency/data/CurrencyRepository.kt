package com.ustinovauliana.currency.data

import com.ustinovauliana.currency.api.CurrencyApi
import com.ustinovauliana.currency.data.model.CurrencyRepoObj
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val api: CurrencyApi,
)  {
    /*
    fun getCurrencyValue(currency: String,
                         baseCurrency: String
    ): Flow<RequestResult<CurrencyRepoObj>> {
        return flow { emit(api.getLatest(currency = currency, baseCurrency = baseCurrency)}
            .map { it.toRequestResult() }
            .map { result ->
                result.map { response ->
                    response.articles.map { it.toArticle() }
                }
            }
    }

     */
}
