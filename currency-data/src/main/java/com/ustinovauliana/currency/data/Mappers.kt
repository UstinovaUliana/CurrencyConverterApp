package com.ustinovauliana.currency.data

import com.ustinovauliana.currency.api.models.CurrencyProperties as CurrencyPropertiesDTO
import com.ustinovauliana.currency.data.model.CurrencyRepoObj


internal fun CurrencyPropertiesDTO.toCurrencyRepoObj(): CurrencyRepoObj {
    return CurrencyRepoObj(
        code = code,
        value = value,
    )
}

