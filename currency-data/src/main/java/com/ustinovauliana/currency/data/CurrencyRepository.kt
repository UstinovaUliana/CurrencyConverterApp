package com.ustinovauliana.currency.data

import android.util.Log
import com.ustinovauliana.currency.api.CurrencyApi
import com.ustinovauliana.currency.api.models.ResponseDTO
import com.ustinovauliana.currency.data.model.CurrencyRepoObj
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val api: CurrencyApi,
)  {

    fun getCurrencyValue(currency: String,
                         baseCurrency: String?
    ): Flow<Map<String, CurrencyRepoObj>> {
        val apiRequest =  flow {
            emit(api.getLatest(currency = currency, baseCurrency = baseCurrency))
        }

        val start = flowOf<ResponseDTO>()
        return merge(apiRequest, start)
            .map { responseDto ->
                Log.d("curdebug", "Repo: result data:" + responseDto.data.toString())

                responseDto.data.map { (key, value) ->
                    Log.d("curdebug", "Repo: result data:" + value.toCurrencyRepoObj().toString())

                   key to value.toCurrencyRepoObj()
                }.toMap()
            }

    }

}
