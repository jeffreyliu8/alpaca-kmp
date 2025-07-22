package org.jeffreyliu.alpacakmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform