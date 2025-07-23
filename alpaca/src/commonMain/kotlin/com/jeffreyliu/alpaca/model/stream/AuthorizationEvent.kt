package com.jeffreyliu.alpaca.model.stream

import com.jeffreyliu.alpaca.model.AlpacaOrder
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamingRequestResponse(
    val action: String? = null,
    val key: String? = null,
    val secret: String? = null,
    val stream: String? = null,
    val data: StreamData? = null,
)


/**
 * Represents the nested "data" object which contains the streams to listen to.
 *
 * @property streams A list of stream names, e.g., ["trade_updates"].
 */
@Serializable
data class StreamData(
    val streams: List<String>? = null,
    val status: String? = null,
    val action: String? = null,
    val at: String? = null,
    @SerialName("event_id")
    val eventId: String? = null,
    val event: String? = null,
    val timestamp: String? = null,
    val order: AlpacaOrder? = null,
)
