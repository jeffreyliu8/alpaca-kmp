package com.jeffreyliu.alpaca.model

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

@Serializable(with = ItemSerializer::class)
sealed interface AlpacaResponseInterface


@Serializable
data class AlpacaSuccessMessageResponse(
    @SerialName("T")
    val type: String,
    val msg: String,
) : AlpacaResponseInterface

@Serializable
data class AlpacaErrorCodeMessageResponse(
    @SerialName("T")
    val type: String,
    val code: Int,
    val msg: String,
) : AlpacaResponseInterface

@Serializable
data class AlpacaSubscriptionRequest(
    @SerialName("T")
    val type: String,
    val trades: List<String>,
    val quotes: List<String>,
    val bars: List<String>,
    val corrections: List<String>? = null,
    val cancelErrors: List<String>? = null,
) : AlpacaResponseInterface

@Serializable
data class AlpacaSubscriptionMessage(
    val action: String,
    val trades: List<String>? = null,
    val quotes: List<String>? = null,
    val bars: List<String>? = null,
    val key: String? = null,
    val secret: String? = null,
    val data: Data? = null,
) {
    @Serializable
    data class Data(
        val streams: List<String>
    )
}


@Serializable
data class TradeSchema(
    // message type, always “t”
    @SerialName("T") val type: String,

    // "S": Symbol, e.g., "TSLA"
    @SerialName("S") val symbol: String,

    // "i": Trade ID
    @SerialName("i") val tradeId: Long,

    // "x": exchange code where the trade occurred
    @SerialName("x") val exchange: String,

    // "p": trade price
    @SerialName("p") val price: Double,

    // "s": trade size
    @SerialName("s") val size: Int,

    // "t": RFC-3339 formatted timestamp with nanosecond precision.
    @SerialName("t") val timestamp: String,

    // "c": Condition flags for the trade
    @SerialName("c") val conditions: List<String>,

    // "z": Tape
    @SerialName("z") val tape: String,
) : AlpacaResponseInterface

@Serializable
data class QuoteSchema(
    // message type, always “q”
    @SerialName("T") val type: String,

    // "S": Symbol, e.g., "AMD"
    @SerialName("S") val symbol: String,

    // "ax": ask exchange code
    @SerialName("ax") val askExchangeCode: String,

    // "ap": Ask price
    @SerialName("ap") val askPrice: Double,

    // "as": Ask size
    @SerialName("as") val askSize: Int,

    // "bx": Bid exchange code
    @SerialName("bx") val bidExchangeCode: String,

    // "bp": Bid price
    @SerialName("bp") val bidPrice: Double,

    // "bs": Bid size
    @SerialName("bs") val bidSize: Int,

    // "t": RFC-3339 formatted timestamp with nanosecond precision.
    @SerialName("t") val timestamp: String,

    // "c": quote condition
    @SerialName("c") val conditions: List<String>,

    // "z": Tape
    @SerialName("z") val tape: String,
) : AlpacaResponseInterface

@Serializable
data class BarSchema(
    // message type, always “b”
    @SerialName("T") val type: String,

    // "S": Symbol, e.g., "AMD"
    @SerialName("S") val symbol: String,

    // "o": ask exchange code
    @SerialName("o") val openPrice: Double,

    // "h": high price
    @SerialName("h") val highPrice: Double,

    // "l": low size
    @SerialName("l") val lowPrice: Double,

    // "c": close price
    @SerialName("c") val closePrice: Double,

    // "v": volume
    @SerialName("v") val volume: Int,

    // "t": RFC-3339 formatted timestamp with nanosecond precision.
    @SerialName("t") val timestamp: String,

    // todo: not sure what this is
    @SerialName("n") val notSure: Int? = null,

    // todo: not sure what this is
    @SerialName("vw") val vw: Double? = null,
) : AlpacaResponseInterface

@Serializable
data class TradeUpdateSchema(
    @SerialName("T") val type: String,
    val event: String? = null,
    val order: AlpacaOrder? = null,
) : AlpacaResponseInterface


object ItemSerializer : JsonContentPolymorphicSerializer<AlpacaResponseInterface>(AlpacaResponseInterface::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<AlpacaResponseInterface> {
        return when (element) {
            is JsonObject -> {
                if (element.containsKey("T") && (element["T"] as JsonPrimitive).content == "success") AlpacaSuccessMessageResponse.serializer()
                else if (element.containsKey("T") && (element["T"] as JsonPrimitive).content == "error") AlpacaErrorCodeMessageResponse.serializer()
                else if (element.containsKey("T") && (element["T"] as JsonPrimitive).content == "subscription") AlpacaSubscriptionRequest.serializer()
                else if (element.containsKey("T") && (element["T"] as JsonPrimitive).content == "t") TradeSchema.serializer()
                else if (element.containsKey("T") && (element["T"] as JsonPrimitive).content == "q") QuoteSchema.serializer()
                else if (element.containsKey("T") && (element["T"] as JsonPrimitive).content == "b") BarSchema.serializer()
                else if (element.containsKey("T") && (element["T"] as JsonPrimitive).content == "trade_updates") TradeUpdateSchema.serializer()
                else throw IllegalArgumentException("Unknown object type: $element")
            }

            else -> throw IllegalArgumentException("Unexpected JSON element type: $element")
        }
    }
}