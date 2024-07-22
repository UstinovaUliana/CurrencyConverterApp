package com.ustinovauliana.currencyconverterapp.domain

import com.ustinovauliana.currencyconverterapp.data.api.CurrencyApi
import com.ustinovauliana.currencyconverterapp.data.api.models.ResponseDTO
import com.ustinovauliana.currencyconverterapp.domain.model.CurrencyRepoObj
import com.ustinovauliana.currencyconverterapp.domain.utils.toCurrencyRepoObj
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val api: CurrencyApi,
) {

    fun getCurrencyValue(
        currency: String,
        baseCurrency: String?
    ): Flow<Map<String, CurrencyRepoObj>> {
        val apiRequest = flow {
            emit(api.getLatest(currency = currency, baseCurrency = baseCurrency))
        }

        val start = flowOf<ResponseDTO>()
        return merge(apiRequest, start)
            .map { responseDto ->
                responseDto.data.map { (key, value) ->
                    key to value.toCurrencyRepoObj()
                }.toMap()
            }
    }
}
