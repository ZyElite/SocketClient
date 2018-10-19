package com.zy.service

import org.smartboot.socket.transport.AioQuickServer
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel


fun main(args: Array<String>) {














    val serverSocketChannel = ServerSocketChannel.open()
    //绑定监听端口10010，并设置最大连接挂起数为1000
    serverSocketChannel.bind(InetSocketAddress(10010), 1000)
    //设置连接为非阻塞式
    serverSocketChannel.configureBlocking(false)
    //开启多路复用选择器
    val selector = Selector.open()
    //注册到 selector中 监听tcp连接事件
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT)
    while (true) {

        val select = selector.select()
        if (select == 0) {
            continue
        }

        val selectedKeys = selector.selectedKeys()
        for (key in selectedKeys) {
            if (key.isAcceptable) {
                //已经接收到新的 到服务器端的连接
                val accept = serverSocketChannel.accept()

                //有新的连接  这个通道不一定有数据
                accept.configureBlocking(false)
                //将新的客户端连接注册到多路复用选择器上监听 等待读取数据
                accept.register(selector, SelectionKey.OP_READ)
            }else if (key.isReadable){
                //有数据可读 获取通道
                val channel = key.channel()

            }
        }

    }


    Thread(Runnable {
        selector.keys()
    }).start()


    val server = AioQuickServer<Int>(10010, IntegerProtocol(), IntegerServerProcessor())
    server.start()

//    val serverThread = ServerThread()
//    Thread(serverThread).start()
//    SocketPacketConfig.setSocketVer(1).setDefaultPacket(true)
//    val a = SocketPacketConfig.getDefaultTailPacket()
//    println(a.size)
//    println(Int.MAX_VALUE)
//    val toBytes = intToBytes(0)
//    println(toBytes)
////    println((toBytes[3].toInt() and 0xFF))
////    println((toBytes[2].toInt() and 0xFF) shl 8)
////    println((toBytes[1].toInt() and 0xFF) shl 16)
////    println((toBytes[0].toInt() and 0xFF) shl 24)
//    println(bytesToInt(toBytes))
}


fun intToBytes(value: Int): ByteArray = byteArrayOf(
        (value shr 24 and 0xFF).toByte(),
        (value shr 16 and 0xFF).toByte(),
        (value shr 8 and 0xFF).toByte(),
        (value and 0xFF).toByte())

fun bytesToInt(bytes: ByteArray): Int = (
        bytes[3].toInt() and 0xFF) or
        ((bytes[2].toInt() and 0xFF) shl 8) or
        ((bytes[1].toInt() and 0xFF) shl 16) or
        ((bytes[0].toInt() and 0xFF) shl 24)
