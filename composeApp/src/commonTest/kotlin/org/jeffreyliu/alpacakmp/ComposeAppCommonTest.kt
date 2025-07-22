package org.jeffreyliu.alpacakmp

import com.jeffreyliu.alpaca.AlpacaClient
import com.jeffreyliu.alpaca.AlpacaClientImpl
import com.jeffreyliu.alpaca.logger.LoggerRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ComposeAppCommonTest {

    @Test
    fun example() = runTest {
        val httpClient = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
//                    isLenient = true
//                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                logger = object : io.ktor.client.plugins.logging.Logger {
                    override fun log(message: String) {
                        co.touchlab.kermit.Logger.v(message, null, "ktor")
                    }
                }
                level = LogLevel.ALL
            }
            install(WebSockets) {
            }
        }

        val logger = LoggerRepositoryImpl()

        val client: AlpacaClient = AlpacaClientImpl(
            isPaper = true,
            apiKey = apiKey,
            apiSecret = apiSecret,
            httpClient = httpClient,
            logger = logger,
        )

        val account = client.getAccount()
        assertEquals("658b447a-cde7-4df6-957b-e79cb14e90ab", account?.id)

    }
}