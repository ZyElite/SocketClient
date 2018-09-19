package com.zy.service

import org.intellij.lang.annotations.PrintFormat


fun main(args: Array<String>) {
    val serverThread = ServerThread()
    Thread(serverThread).start()
//    SocketPacketConfig.setSocketVer(1).setDefaultPacket(true)
//    val a = SocketPacketConfig.getDefaultTailPacket()
//    println(a.size)
//    println(Int.MAX_VALUE)
//    val toBytes = intToBytes(Int.MAX_VALUE)
////    println(toBytes)
////    println((toBytes[3].toInt() and 0xFF))
////    println((toBytes[2].toInt() and 0xFF) shl 8)
////    println((toBytes[1].toInt() and 0xFF) shl 16)
////    println((toBytes[0].toInt() and 0xFF) shl 24)
//    println(bytesToInt(toBytes))
}


