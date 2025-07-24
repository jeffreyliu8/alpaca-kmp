package com.jeffreyliu.alpaca

import kotlinx.coroutines.flow.Flow
import com.jeffreyliu.alpaca.model.AlpacaAccount
import com.jeffreyliu.alpaca.model.AlpacaOrder
import com.jeffreyliu.alpaca.model.AlpacaOrderIdStatus
import com.jeffreyliu.alpaca.model.AlpacaOrderRequest
import com.jeffreyliu.alpaca.model.AlpacaPosition
import com.jeffreyliu.alpaca.model.AlpacaReplaceOrderRequest
import com.jeffreyliu.alpaca.model.AlpacaResponseInterface
import com.jeffreyliu.alpaca.model.AlpacaStockExchangeOption
import com.jeffreyliu.alpaca.model.AlpacaTrades
import com.jeffreyliu.alpaca.model.stream.StreamingRequestResponse

interface AlpacaClient {

    /**
     * The account update streaming sends events whenever an account is updated.
     * This can be used to listen for changes in the account in real-time.
     *
     * @return a flow of account updates
     */
    fun streamAccount(): Flow<StreamingRequestResponse>

    /**
     * Get the Alpaca account information
     *
     * @return AlpacaAccount, null for any error
     */
    suspend fun getAccount(): AlpacaAccount?

    /**
     * Get all positions
     *
     * @return a list of all open positions, null for any error
     */
    suspend fun getPositions(): List<AlpacaPosition>?

    /**
     * Get position for a symbol
     *
     * @param symbol the symbol to get the position for
     * @return the position for the given symbol, or null if it doesn't exist
     */
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
     * @param symbol symbol or asset_id
     * @param cancelOrders If true is specified, cancel all open orders before liquidating all positions.
     * @param qty The number of shares to liquidate. It can be the number of shares to liquidate
     * or "100%" to liquidate all shares.
     * @param percentage percentage of position to liquidate. Must be between 0 and 100. Would only
     * sell fractional if position is originally fractional. Can accept up to 9 decimal points.
     * Cannot work with qty
     */
    suspend fun closePosition(
        symbol: String,
        cancelOrders: Boolean,
        qty: String? = null,
        percentage: String? = null
    ): AlpacaOrder?

    /**
     * Place an order with Alpaca
     *
     * @param orderRequest The order request details
     * @return AlpacaOrder containing the order details
     */
    suspend fun placeOrder(orderRequest: AlpacaOrderRequest): AlpacaOrder?

    /**
     * Retrieves a list of orders for the account, filtered by the supplied query parameters.
     *
     * @param status Order status to be queried. open, closed or all. Defaults to open.
     * @param limit The maximum number of orders in response. Defaults to 50 and max is 500.
     * @param after The response will include only ones submitted after this timestamp (exclusive.)
     * @param until The response will include only ones submitted until this timestamp (exclusive.)
     * @param direction The chronological order of response based on the submission time. asc or desc. Defaults to desc.
     * @param nested If true, the result will roll up multi-leg orders under the legs field of primary order.
     * @param symbols A comma-separated list of symbols to filter by
     */
    suspend fun getOrders(
        status: String = "open",
        limit: Int = 500,
        after: String? = null,
        until: String? = null,
        direction: String = "desc",
        nested: Boolean? = null,
        symbols: List<String>? = null
    ): List<AlpacaOrder>

    /**
     * Retrieves a single order for the given order_id.
     *
     * @param orderId the id of the order to retrieve
     */
    suspend fun getOrder(orderId: String): AlpacaOrder?

    /**
     * Get an order by its client order id
     *
     * @param clientOrderId the client order id of the order to retrieve
     * @return the order with the given client order id, or null if it doesn't exist
     */
    suspend fun getOrderByClientId(clientOrderId: String): AlpacaOrder?

    /**
     * Replaces a single order with a new one.
     *
     * @param orderId the id of the order to replace
     * @param replaceOrderRequest the request to replace the order with
     * @return the new order
     */
    suspend fun replaceOrder(
        orderId: String,
        replaceOrderRequest: AlpacaReplaceOrderRequest
    ): AlpacaOrder

    /**
     * Attempts to cancel all open orders. A response will be provided for each order that is
     * attempted to be cancelled. If an order is no longer cancelable, the server will respond
     * with status 500 and reject the request.
     *
     * @return a list of the cancelled orders
     */
    suspend fun cancelAllOrders(): List<AlpacaOrderIdStatus>

    /**
     * Cancel an order by its id
     *
     * @param orderId the id of the order to cancel
     * @return the status of the cancelled order
     */
    suspend fun cancelOrder(orderId: String): AlpacaOrderIdStatus?

    /**
     * Monitor stock price using WebSocket
     * @param symbols The stock symbol to monitor (e.g., "AAPL")
     * @return Flow of AlpacaStockPriceUpdate containing real-time stock price updates
     */
    fun monitorStockPrice(
        symbols: Set<String>,
        stockExchange: AlpacaStockExchangeOption = AlpacaStockExchangeOption.IEX,
    ): Flow<List<AlpacaResponseInterface>>


    suspend fun getTrades(
        symbol: String,
        start: String?, //Filter data equal to or after this time in RFC-3339 format. Fractions of a second are not accepted. Defaults to the current day in CT.
        end: String?, //Filter data equal to or before this time in RFC-3339 format. Fractions of a second are not accepted. Default value is now.
        limit: Int = 10000, //Number of data points to return. Must be in range 1-10000, defaults to 1000
        pageToken: String? = null //Pagination token to continue from.
    ): AlpacaTrades?
}
