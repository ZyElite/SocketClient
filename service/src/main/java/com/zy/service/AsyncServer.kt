package com.zy.service

import java.net.InetSocketAddress
import java.nio.channels.AsynchronousServerSocketChannel


fun main(args: Array<String>) {

    val asynchronousServerSocketChannel = AsynchronousServerSocketChannel
            .open()
            .bind(InetSocketAddress(10010))

    val future = asynchronousServerSocketChannel.accept()

}