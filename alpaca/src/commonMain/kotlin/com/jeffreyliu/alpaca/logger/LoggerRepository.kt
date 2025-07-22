package com.jeffreyliu.alpaca.logger


interface LoggerRepository {
    fun i(message: String)
    fun w(message: String)
    fun v(message: String)
    fun d(message: String)
    fun e(message: String)
}
