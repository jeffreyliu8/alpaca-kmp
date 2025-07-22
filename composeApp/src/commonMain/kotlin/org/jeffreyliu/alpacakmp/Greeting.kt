package org.jeffreyliu.alpacakmp

import  com.jeffreyliu.alpaca.platform

class Greeting {
    private val platform = platform()

    fun greet(): String {
        return "Hello, ${platform}!"
    }
}