package com.zy.service

import org.intellij.lang.annotations.PrintFormat


fun main(args: Array<String>) {
    val serverThread = ServerThread()
    Thread(serverThread).start()
//    println(Int.MAX_VALUE)
//    val toBytes = intToBytes(Int.MAX_VALUE)
////    println(toBytes)
////    println((toBytes[3].toInt() and 0xFF))
////    println((toBytes[2].toInt() and 0xFF) shl 8)
////    println((toBytes[1].toInt() and 0xFF) shl 16)
////    println((toBytes[0].toInt() and 0xFF) shl 24)
//    println(bytesToInt(toBytes))
}

private fun intToBytes(value: Int): ByteArray = byteArrayOf(
        (value shr 24 and 0xFF).toByte(),
        (value shr 16 and 0xFF).toByte(),
        (value shr 8 and 0xFF).toByte(),
        (value and 0xFF).toByte())

private fun bytesToInt(bytes: ByteArray): Int = (
        bytes[3].toInt() and 0xFF) or
        ((bytes[2].toInt() and 0xFF) shl 8) or
        ((bytes[1].toInt() and 0xFF) shl 16) or
        ((bytes[0].toInt() and 0xFF) shl 24)

