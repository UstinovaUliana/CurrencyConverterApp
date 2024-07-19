package com.ustinovauliana.features.main

import android.util.Log
import com.ustinovauliana.currency.data.CurrencyRepository
import com.ustinovauliana.currency.data.RequestResult
import com.ustinovauliana.currency.data.map
import com.ustinovauliana.currency.data.model.CurrencyRepoObj
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetCurrencyValueUseCase @Inject constructor(private val repository: CurrencyRepository) {

   operator fun invoke(baseCurrency: String?, currency: String): Flow<RequestResult<CurrencyUI>> {
        return repository.getCurrencyValue(baseCurrency = baseCurrency, currency = currency)
            .map { requestResult ->
                Log.d("curdebug", "UseCase: requestResult" + requestResult.data.toString())
                requestResult.map {  it.toCurrencyUi() }
                }
   }
}


private fun CurrencyRepoObj.toCurrencyUi(): CurrencyUI {
    return CurrencyUI(
        eurValue = eurProperties.value,
        usdValue = usdProperties.value,
        rubValue = rubProperties.value,
        gbpValue = gbpProperties.value
    )
}

