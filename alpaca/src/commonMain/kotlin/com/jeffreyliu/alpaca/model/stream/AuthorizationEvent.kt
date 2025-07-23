package com.jeffreyliu.alpaca.model.stream

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
)
