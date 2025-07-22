package com.jeffreyliu.alpaca.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
data class AlpacaTrade(
    @SerialName("t") val timestamp: String, //Timestamp in RFC-3339 format with nanosecond precision.
    @SerialName("x") val x: String, //Exchange where the trade happened.
    @SerialName("p") val price: Double, //Trade price.
    @SerialName("s") val size: Int,
    @SerialName("c") val conditions: List<String>, //Trade conditions.
    @SerialName("i") val id: Long, //Trade ID.
    @SerialName("z") val tape: String //Tape.
)


@OptIn(ExperimentalTime::class)
fun String.timestampInstantStringToMilliLong(): Long {
    return Instant.parse(this).toEpochMilliseconds()
}