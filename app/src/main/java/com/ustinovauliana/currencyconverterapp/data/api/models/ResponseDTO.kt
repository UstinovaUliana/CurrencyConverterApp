package com.ustinovauliana.currencyconverterapp.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO(
    @SerialName("meta") val meta: Meta,
    @SerialName("data") val data: Map<String, CurrencyProperties>
)

@Serializable
data class Meta(
    @SerialName("last_updated_at") val lastUpdatedAt: String
)

@Serializable
data class CurrencyProperties(
    @SerialName("code") val code: String,
    @SerialName("value") val value: Float
)
