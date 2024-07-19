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
    ): Flow<RequestResult<CurrencyRepoObj>> {
        val apiRequest =  flow {
            emit(api.getLatest(currency = currency, baseCurrency = baseCurrency))
        }
            .map {
                Log.d("curdebug", "Repo: toreqres data:" + it.toRequestResult().data.toString())

                it.toRequestResult()
            }


        val start = flowOf<RequestResult<ResponseDTO>>(RequestResult.InProgress())
        return merge(apiRequest, start)
            .map { result ->
                Log.d("curdebug", "Repo: result data:" + result.data.toString())
                result.map {
                    it.toCurrencyRepoObj()
                }
            }
    }

}
