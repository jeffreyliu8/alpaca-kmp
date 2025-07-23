package com.jeffreyliu.alpaca

import kotlinx.coroutines.flow.Flow
import com.jeffreyliu.alpaca.model.AlpacaAccount
import com.jeffreyliu.alpaca.model.AlpacaAccountAndTime
import com.jeffreyliu.alpaca.model.AlpacaOrder
import com.jeffreyliu.alpaca.model.AlpacaOrderIdStatus
import com.jeffreyliu.alpaca.model.AlpacaOrderRequest
import com.jeffreyliu.alpaca.model.AlpacaPosition
import com.jeffreyliu.alpaca.model.AlpacaReplaceOrderRequest
import com.jeffreyliu.alpaca.model.AlpacaResponseInterface
import com.jeffreyliu.alpaca.model.AlpacaTrades
import com.jeffreyliu.alpaca.model.stream.StreamingRequestResponse
import kotlin.time.ExperimentalTime

interface AlpacaClient {

    fun streamAccount(): Flow<StreamingRequestResponse>

    /**
     * Get the Alpaca account information
     * @return AlpacaAccount
     */
    suspend fun getAccount(): AlpacaAccount?

    suspend fun getPositions(): List<AlpacaPosition>?

    suspend fun getPosition(symbol: String): AlpacaPosition?

    /**
     * Closes (liquidates) all of the account’s open long and short positions. A response will be
     * provided for each order that is attempted to be cancelled. If an order is no longer
     * cancelable, the server will respond with status 500 and reject the request.
     *
     * @param cancelOrders If true is specified, cancel all open orders before liquidating all
     * positions.
     */
    suspend fun closeAllPositions(cancelOrders: Boolean = false): List<AlpacaPosition>

    /**
     * Closes (liquidates) the account’s open position for the given symbol. Works for both long
     * and short positions.
     *
     * @param symbol symbol or asset_id, Path Parameters
     * @param cancelOrders If true is specified, cancel all open orders before liquidating all positions.
     * @param qty the number of shares to liquidate. Can accept up to 9 decimal points. Cannot work with percentage, Query Parameters
     * @param percentage percentage of position to liquidate. Must be between 0 and 100. Would only sell fractional if position is originally fractional. Can accept up to 9 decimal points. Cannot work with qty
     */
    suspend fun closePosition(
        symbol: String,
        cancelOrders: Boolean,
        qty: String? = null,
        percentage: String? = null
    ): AlpacaOrder?

    @OptIn(ExperimentalTime::class)
    fun getAccountFlow(elapseTimeMilliseconds: Long = 10_000L): Flow<AlpacaAccountAndTime>

    /**
     * Place an order with Alpaca
     * @param orderRequest The order request details
     * @return AlpacaOrder containing the order details
     */
    suspend fun placeOrder(orderRequest: AlpacaOrderRequest): AlpacaOrder?

    /**
     * Retrieves a list of orders for the account, filtered by the supplied query parameters.
     */
    suspend fun getOrders(
        status: String = "open", //Order status to be queried. open, closed or all. Defaults to open.
        limit: Int = 500, // The maximum number of orders in response. Defaults to 50 and max is 500.
        after: String? = null, //The response will include only ones submitted after this timestamp (exclusive.)
        until: String? = null, //The response will include only ones submitted until this timestamp (exclusive.)
        direction: String = "desc", //The chronological order of response based on the submission time. asc or desc. Defaults to desc.
        nested: Boolean? = null, //If true, the result will roll up multi-leg orders under the legs field of primary order.
        symbols: List<String>? = null //A comma-separated list of symbols to filter by
    ): List<AlpacaOrder>

    /**
     * Retrieves a single order for the given order_id.
     */
    suspend fun getOrder(orderId: String): AlpacaOrder?

    suspend fun getOrderByClientId(clientOrderId: String): AlpacaOrder?

    suspend fun replaceOrder(
        orderId: String,
        replaceOrderRequest: AlpacaReplaceOrderRequest
    ): AlpacaOrder

    suspend fun cancelAllOrders(): List<AlpacaOrderIdStatus>

    suspend fun cancelOrder(orderId: String): AlpacaOrderIdStatus?

    /**
     * Monitor stock price using WebSocket
     * @param symbols The stock symbol to monitor (e.g., "AAPL")
     * @return Flow of AlpacaStockPriceUpdate containing real-time stock price updates
     */
    fun monitorStockPrice(symbols: Set<String>): Flow<List<AlpacaResponseInterface>>


    suspend fun getTrades(
        symbol: String,
        start: String?, //Filter data equal to or after this time in RFC-3339 format. Fractions of a second are not accepted. Defaults to the current day in CT.
        end: String?, //Filter data equal to or before this time in RFC-3339 format. Fractions of a second are not accepted. Default value is now.
        limit: Int = 10000, //Number of data points to return. Must be in range 1-10000, defaults to 1000
        pageToken: String? = null //Pagination token to continue from.
    ): AlpacaTrades?
}
