package com.ustinovauliana.currencyconverterapp.domain.utils

import com.ustinovauliana.currencyconverterapp.data.api.models.CurrencyProperties as CurrencyPropertiesDTO
import com.ustinovauliana.currencyconverterapp.domain.model.CurrencyRepoObj
import com.ustinovauliana.currencyconverterapp.presentation.models.CurrencyUI


internal fun CurrencyPropertiesDTO.toCurrencyRepoObj(): CurrencyRepoObj {
    return CurrencyRepoObj(
        code = code,
        value = value,
    )
}

internal fun CurrencyRepoObj.toCurrencyUI(): CurrencyUI {
    return CurrencyUI(
        code = code,
        value = value,
    )
}

