package com.zy.service

import org.smartboot.socket.Protocol
import org.smartboot.socket.transport.AioSession
import java.nio.ByteBuffer

class IntegerProtocol : Protocol<Int> {
    override fun decode(readBuffer: ByteBuffer?, session: AioSession<Int>?): Int {
        if (readBuffer?.remaining() ?: 0 < INT_LENGTH) {
            return 0
        }
        return readBuffer?.int ?: 0
    }

    override fun encode(msg: Int, session: AioSession<Int>?): ByteBuffer {
        val byteBuffer = ByteBuffer.allocate(INT_LENGTH)
        byteBuffer.putInt(msg)
        byteBuffer.flip()
        return byteBuffer
    }

    companion object {
        const val INT_LENGTH = 4
    }

}