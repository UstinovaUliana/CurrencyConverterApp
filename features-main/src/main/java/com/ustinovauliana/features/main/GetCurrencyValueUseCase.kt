package com.ustinovauliana.features.main

import android.util.Log
import com.ustinovauliana.currency.data.CurrencyRepository
import com.ustinovauliana.currency.data.model.CurrencyRepoObj
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetCurrencyValueUseCase @Inject constructor(private val repository: CurrencyRepository) {

   operator fun invoke(baseCurrency: String?, currency: String): Flow<Map<String, CurrencyUI>> {
        return repository.getCurrencyValue(baseCurrency = baseCurrency, currency = currency)
            .map { responseMap ->
                responseMap.map { (key, value) ->
                    key to value.toCurrencyUI()
                }.toMap()
            }
   }
}

private fun CurrencyRepoObj.toCurrencyUI(): CurrencyUI {
    return CurrencyUI(
        code = code,
        value = value,
    )
}

