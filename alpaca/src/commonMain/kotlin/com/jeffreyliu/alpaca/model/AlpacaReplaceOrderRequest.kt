package com.jeffreyliu.alpaca.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlpacaReplaceOrderRequest(
    val qty: String? = null,
    @SerialName("time_in_force") val timeInForce: String? = null,
    @SerialName("limit_price") val limitPrice: String? = null,
    @SerialName("stop_price") val stopPrice: String? = null,
    val trail: String? = null,
    @SerialName("client_order_id") val clientOrderId: String? = null
)
