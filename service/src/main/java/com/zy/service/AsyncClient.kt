package com.zy.service

import org.smartboot.socket.transport.AioQuickClient
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler


fun main(args: Array<String>) {

//    val messageProcessor = IntegerClientProcessor()
//    val client = AioQuickClient<Int>("192.168.98.110", 10010, IntegerProtocol(), messageProcessor)
//    client.start()

    //创建异步套接字客户端
    val asynchronousSocketChannel = AsynchronousSocketChannel.open()
    //等待连接成功
    asynchronousSocketChannel.connect(InetSocketAddress(InetAddress.getLocalHost(), 10010)).get()
    val buffer = ByteBuffer.allocate(1024)
    buffer.put("AIO Client".toByteArray())
    //切换到读模式
    buffer.flip()

    asynchronousSocketChannel.write(buffer, Conversation(), object : CompletionHandler<Int, Conversation<ByteBuffer>> {
        override fun completed(result: Int?, attachment: Conversation<ByteBuffer>?) {
            println(result)
        }

        override fun failed(exc: Throwable, attachment: Conversation<ByteBuffer>?) {
            println(exc.message)
        }

    })

}