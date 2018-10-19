package com.zy.service

import com.sun.org.apache.xpath.internal.operations.Bool
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.AsynchronousSocketChannel

/**
 * 附件
 */
class Attachment {
    private var server: AsynchronousServerSocketChannel? = null
    private var client: AsynchronousSocketChannel? = null
    private var isReadModel: Boolean = false
    private var buffer: ByteBuffer? = null

    fun setServer(serverSocketChannel: AsynchronousServerSocketChannel) {
        server = serverSocketChannel
    }

    fun getServer() = server

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