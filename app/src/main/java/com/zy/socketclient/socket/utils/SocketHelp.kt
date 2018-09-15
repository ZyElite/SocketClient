package com.zy.socketclient.socket.utils

object SocketHelp {
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


    fun byteMerger(first: ByteArray, second: ByteArray): ByteArray {
        val third = ByteArray(first.size + second.size)
        System.arraycopy(first, 0, third, 0, first.size)
        System.arraycopy(second, 0, third, first.size, second.size)
        return third
    }

    /**
     * 0-4   版本号
     * 4-8   总长度
     * 8-12  内容长度
     */
    fun getHeadData(bodyLen: Int): ByteArray {
        val verLenB = intToBytes(1)
        val verAndBodyB = intToBytes(verLenB.size + bodyLen)
        val bodyLenB = intToBytes(bodyLen)
        return byteMerger(byteMerger(verLenB, verAndBodyB), bodyLenB)
    }
}