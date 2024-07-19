package com.ustinovauliana.currency.data.model

data class CurrencyRepoObj (
    val rubProperties: CurrencyProperties,
    val eurProperties: CurrencyProperties,
    val usdProperties: CurrencyProperties,
    val gbpProperties: CurrencyProperties,
)

data class CurrencyProperties(
    val value: Float
)
