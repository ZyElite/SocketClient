package com.zy.service

import org.smartboot.socket.transport.AioSession
import java.nio.ByteBuffer

/**
 * 消息编解码
 */
interface Protocol<T> {
    /**
     * 数据按protocol进行解码
     */
    fun decode(readBuffer: ByteBuffer, attachment: Attachment<T>): T

    /**
     * 数据按protocol进行编码
     */
    fun encode(msg: T, attachment: Attachment<T>): ByteBuffer
}