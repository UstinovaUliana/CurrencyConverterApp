package com.ustinovauliana.currencyconverterapp.domain

import com.ustinovauliana.currencyconverterapp.domain.utils.toCurrencyUI
import com.ustinovauliana.currencyconverterapp.presentation.models.CurrencyUI
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
