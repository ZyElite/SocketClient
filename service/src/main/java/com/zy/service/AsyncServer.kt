package com.zy.service

import org.intellij.lang.annotations.PrintFormat
import org.smartboot.socket.transport.AioQuickServer
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler
import java.util.*


fun main(args: Array<String>) {

//    val server = AioQuickServer<Int>(10010, IntegerProtocol(), IntegerServerProcessor())
//    server.start()

    val asynchronousServerSocketChannel = AsynchronousServerSocketChannel
            .open()
            .bind(InetSocketAddress(10010))

    asynchronousServerSocketChannel.accept(asynchronousServerSocketChannel, object : CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {

        override fun completed(result: AsynchronousSocketChannel, attachment: AsynchronousServerSocketChannel) {
            //连接地址
            val socketAddress = result?.remoteAddress
            println("completed：$socketAddress")
            //当获取到连接 重新等待新的连接接入
            attachment?.accept(attachment, this)
            result.read(ByteBuffer.allocate(1024), Conversation(), ReadCompletionHandler<ByteArray>())
        }

        override fun failed(exc: Throwable, attachment: AsynchronousServerSocketChannel) {
            println("failed：${exc?.message}")
        }
    })

    // 为了防止 main 线程退出
    try {
        Thread.currentThread().join()
    } catch (e: InterruptedException) {
    }

}

