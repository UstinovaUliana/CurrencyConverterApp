package com.ustinovauliana.currency.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyDTO (
    @SerialName("RUB") val rubProperties: CurrencyProperties,
    @SerialName("EUR") val eurProperties: CurrencyProperties,
    @SerialName("USD") val usdProperties: CurrencyProperties,
    @SerialName("GBP") val gbpProperties: CurrencyProperties,
)

@Serializable
data class CurrencyProperties(
    @SerialName("code") val code : String,
    @SerialName("value") val value: Float
)
