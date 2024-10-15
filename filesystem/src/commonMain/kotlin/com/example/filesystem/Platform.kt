package com.example.filesystem

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform