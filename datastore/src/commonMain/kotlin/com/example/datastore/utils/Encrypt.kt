package com.example.datastore.utils

expect object Encrypt {

    fun encrypt(key: String, bytes: ByteArray): ByteArray
    fun decrypt(key: String, bytes: ByteArray): ByteArray
}
