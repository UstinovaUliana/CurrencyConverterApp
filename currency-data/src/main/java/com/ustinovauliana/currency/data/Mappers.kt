package com.ustinovauliana.currency.data

import com.ustinovauliana.currency.api.models.CurrencyDTO
import com.ustinovauliana.currency.api.models.ResponseDTO
import com.ustinovauliana.currency.data.model.CurrencyProperties
import com.ustinovauliana.currency.api.models.CurrencyProperties as CurrencyPropertiesDTO
import com.ustinovauliana.currency.data.model.CurrencyRepoObj


internal fun ResponseDTO.toCurrencyRepoObj(): CurrencyRepoObj {
    return CurrencyRepoObj(
        rubProperties = data.rubProperties.toCurrencyProperties(),
        eurProperties = data.eurProperties.toCurrencyProperties(),
        usdProperties = data.usdProperties.toCurrencyProperties(),
        gbpProperties = data.gbpProperties.toCurrencyProperties()
    )
}
internal fun CurrencyPropertiesDTO.toCurrencyProperties(): CurrencyProperties {
    return CurrencyProperties(value = value)
}
