package com.zy.service

import java.nio.ByteBuffer
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.AsynchronousSocketChannel

class Conversation<T> {
//    private var server: AsynchronousServerSocketChannel? = null

    /**
     * 客户端  异步套接字通道
     */
    private var client: AsynchronousSocketChannel? = null
    private var isReadModel: Boolean = false
    private var buffer: ByteBuffer? = null
//    fun setServer(serverSocketChannel: AsynchronousServerSocketChannel) {
//        server = serverSocketChannel
//    }
//
//    fun getServer() = server

    fun setClient(client: AsynchronousSocketChannel) {
        this.client = client
    }

    fun getClient() = client

    fun setReadModel(readModel: Boolean) {
        isReadModel = readModel
    }

    fun isReadModel() = isReadModel

    fun setBuffer(buffer: ByteBuffer) {
        this.buffer = buffer
    }

    fun getBuffer() = buffer

}