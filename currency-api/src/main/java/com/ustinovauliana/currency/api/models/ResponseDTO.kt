package com.ustinovauliana.currency.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO (
    @SerialName("meta") val meta: Meta,
    @SerialName("data") val data: CurrencyDTO
)

@Serializable
data class Meta (
    @SerialName("last_updated_at") val lastUpdatedAt: String
)
