package com.jeffreyliu.alpaca.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlpacaTrades(
    @SerialName("trades") val trades: List<AlpacaTrade>?,
    @SerialName("symbol") val symbol: String,
    @SerialName("next_page_token") val nextPageToken: String?
)