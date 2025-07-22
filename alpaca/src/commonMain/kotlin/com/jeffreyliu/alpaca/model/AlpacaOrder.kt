package com.jeffreyliu.alpaca.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an order request to be sent to Alpaca API
 */
@Serializable
data class AlpacaOrderRequest(
    val symbol: String,
    val qty: String? = null,
    @SerialName("notional") val notionalValue: String? = null,
    val side: String, // buy or sell
    val type: String, // market, limit, stop, stop_limit
    @SerialName("time_in_force") val timeInForce: String, // day, gtc, opg, cls, ioc, fok
    @SerialName("limit_price") val limitPrice: String? = null,
    @SerialName("stop_price") val stopPrice: String? = null,
    @SerialName("client_order_id") val clientOrderId: String? = null,
    @SerialName("extended_hours") val extendedHours: Boolean? = null,
    @SerialName("order_class") val orderClass: String? = null,
    @SerialName("take_profit") val takeProfit: TakeProfitParams? = null,
    @SerialName("stop_loss") val stopLoss: StopLossParams? = null,
    @SerialName("trail_price") val trailPrice: String? = null,
    @SerialName("trail_percent") val trailPercent: String? = null
)

/**
 * Represents a take profit order parameter
 */
@Serializable
data class TakeProfitParams(
    @SerialName("limit_price") val limitPrice: String
)

/**
 * Represents a stop loss order parameter
 */
@Serializable
data class StopLossParams(
    @SerialName("stop_price") val stopPrice: String,
    @SerialName("limit_price") val limitPrice: String? = null
)

/**
 * Represents an order response from Alpaca API
 */
@Serializable
data class AlpacaOrder(
    val id: String,
    @SerialName("client_order_id") val clientOrderId: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String? = null,
    @SerialName("submitted_at") val submittedAt: String? = null,
    @SerialName("filled_at") val filledAt: String? = null,
    @SerialName("expired_at") val expiredAt: String? = null,
    @SerialName("canceled_at") val canceledAt: String? = null,
    @SerialName("failed_at") val failedAt: String? = null,
    @SerialName("replaced_at") val replacedAt: String? = null,
    @SerialName("replaced_by") val replacedBy: String? = null,
    @SerialName("replaces") val replaces: String? = null,
    val asset_id: String,
    val symbol: String,
    @SerialName("asset_class") val assetClass: String,
    val notional: String? = null,
    val qty: String? = null,
    @SerialName("filled_qty") val filledQty: String? = null,
    val type: String,
    val side: String,
    @SerialName("time_in_force") val timeInForce: String,
    @SerialName("limit_price") val limitPrice: String? = null,
    @SerialName("stop_price") val stopPrice: String? = null,
    @SerialName("filled_avg_price") val filledAvgPrice: String? = null,
    @SerialName("order_class") val orderClass: String? = null,
    @SerialName("order_type") val orderType: String? = null,
    val status: String,
    @SerialName("extended_hours") val extendedHours: Boolean? = null,
    @SerialName("legs") val legs: List<AlpacaOrder>? = null,
    @SerialName("trail_price") val trailPrice: String? = null,
    @SerialName("trail_percent") val trailPercent: String? = null,
    @SerialName("hwm") val hwm: String? = null,
    @SerialName("position_intent") val positionIntent: String? = null,
    val subtag: String? = null,
    val source: String? = null,
    @SerialName("expires_at") val expiresAt: String? = null,
)