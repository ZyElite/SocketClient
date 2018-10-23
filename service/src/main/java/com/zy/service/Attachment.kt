package com.zy.service

import com.sun.org.apache.xpath.internal.operations.Bool
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.AsynchronousSocketChannel

/**
 * 附件
 */
class Attachment<T> {
    /**
     * 客户端  异步套接字通道
     */
    private var client: AsynchronousSocketChannel? = null
    private var isReadModel: Boolean = false
    /**
     * 读缓冲。
     *
     * 大小取决于AioQuickClient/AioQuickServer设置的setReadBufferSize
     */
    private var readBuffer: ByteBuffer? = null
    /**
     * 写缓冲
     */
    private var writeBuffer: ByteBuffer? = null
//    fun setServer(serverSocketChannel: AsynchronousServerSocketChannel) {
//        server = serverSocketChannel
//    }
//
//    fun getServer() = server


    fun setReadBuffer(read: ByteBuffer) {
        readBuffer = read
    }

    fun getReadBuffer() = readBuffer

    fun setClient(client: AsynchronousSocketChannel) {
        this.client = client
    }

    fun getClient() = client

    fun setReadModel(readModel: Boolean) {
        isReadModel = readModel
    }

    fun isReadModel() = isReadModel

}