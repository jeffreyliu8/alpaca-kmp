package com.jeffreyliu.alpaca.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlpacaPosition(
    @SerialName("asset_id") val assetId: String,
    val symbol: String,
    val exchange: String,
    @SerialName("asset_class") val assetClass: String,
    @SerialName("avg_entry_price") val avgEntryPrice: String,
    val qty: String,
    val side: String,
    @SerialName("market_value") val marketValue: String,
    @SerialName("cost_basis") val costBasis: String,
    @SerialName("unrealized_pl") val unrealizedPl: String,
    @SerialName("unrealized_plpc") val unrealizedPlpc: String,
    @SerialName("unrealized_intraday_pl") val unrealizedIntradayPl: String,
    @SerialName("unrealized_intraday_plpc") val unrealizedIntradayPlpc: String,
    @SerialName("current_price") val currentPrice: String,
    @SerialName("lastday_price") val lastdayPrice: String,
    @SerialName("change_today") val changeToday: String,
    @SerialName("asset_marginable") val assetMarginable: Boolean? = null,
    @SerialName("qty_available") val qtyAvailable: String? = null,
)
