package com.zy.service

import java.util.*

/**
 * 发送消息 信息包处理类
 */
object SocketPacketConfig {


    private val MAXDATALEN = 1024//处理数据缓冲池的长度
    private val RECEIVEDATALEN = 1024//读取网络数据包最大长度
    private var siglePackageLen = 0//提取出包的长度
    private var sequenceLen = 0//当前缓冲区内数据长度
    private val buffSequencePackage = ByteArray(MAXDATALEN)//数据缓冲池


    private const val TAIL_PACKET = "SocketClient"
    private var version: Int = 0
    private var headData: ByteArray? = null
    private var tailData: ByteArray? = null
    private var headPacketLength = 8
    private var tailPacketLength = 0
    private var heartbeat: ByteArray? = null
    private var defaultHeadData: ByteArray? = null
    private var defaultTailData: ByteArray? = null
    private var addPacket = true
    private var packetLength = 0
    private const val TAIL_LEN = 2

    fun setSocketVer(ver: Int): SocketPacketConfig {
        version = ver
        return this
    }


    fun setHeadData(head: ByteArray): SocketPacketConfig {
        if (headData == null) {
            headData = head
        }
        return this
    }

    fun setTailData(tail: ByteArray): SocketPacketConfig {
        if (tailData == null) {
            tailData = tail
        }
        return this
    }

    fun setHeadPacketLength(len: Int): SocketPacketConfig {
        headPacketLength = len
        return this
    }

    fun setTailPacketLength(len: Int): SocketPacketConfig {
        tailPacketLength = len
        return this
    }


    fun setHeartBeat(heart: ByteArray): SocketPacketConfig {
        heartbeat = heart
        return this
    }

    fun getHeartBeat(): ByteArray? {
        return heartbeat
    }

    /**
     * 设置默认包头包尾
     * 0-3   版本号
     * 4-5   0x1 0x2
     * 版本号 +  固定长度
     */
    fun setDefaultPacket(boolean: Boolean): SocketPacketConfig {
        addPacket = boolean
        return this
    }


    fun getDefaultHeadPacket(): ByteArray {
        return defaultHeadData ?: ByteArray(0)
    }

    fun getDefaultTailPacket(): ByteArray {
        return defaultTailData ?: ByteArray(0)
    }

    private fun intToBytes(value: Int): ByteArray = byteArrayOf(
            (value shr 24 and 0xFF).toByte(),
            (value shr 16 and 0xFF).toByte(),
            (value shr 8 and 0xFF).toByte(),
            (value and 0xFF).toByte())

    private fun bytesToInt(bytes: ByteArray): Int = (
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

}