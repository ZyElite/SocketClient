package com.zy.socket.callback


interface SocketCustomizeReceive {
    /**
     * 包头长度
     */
    fun headLength(): Int

    /**
     * 返回包体长度
     */
    fun bodyLength(head: ByteArray): Int


}