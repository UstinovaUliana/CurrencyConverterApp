package com.ustinovauliana.features.main

import com.ustinovauliana.currency.data.CurrencyRepository
import com.ustinovauliana.currency.data.RequestResult
import com.ustinovauliana.currency.data.model.CurrencyRepoObj
import javax.inject.Inject

internal class GetCurrencyValueUseCase  @Inject constructor(private val repository: CurrencyRepository) {
   /* operator fun invoke(): RequestResult<CurrencyUI> {
        return repository.getCurrencyValue()
    }

    */
}

/*
private fun CurrencyRepoObj.toUiCurrency(): CurrencyUI {
    return CurrencyUI(
        value = value
    )
}

 */
