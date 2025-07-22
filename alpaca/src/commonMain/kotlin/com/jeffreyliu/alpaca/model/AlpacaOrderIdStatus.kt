package com.jeffreyliu.alpaca.model

import kotlinx.serialization.Serializable


@Serializable
data class AlpacaOrderIdStatus(
    val id: String,
    val status:Int
)