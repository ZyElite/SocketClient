package com.zy.socketclient.socket

import com.zy.socketclient.socket.utils.SocketHelp.byteMerger
import com.zy.socketclient.socket.utils.SocketHelp.intToBytes

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
    private var headPacketLength = 0
    private var tailPacketLength = 0
    private var heartbeat: ByteArray? = null
    private var defaultHeadData: ByteArray? = null
    private var defaultTailData: ByteArray? = null
    private var addPacket = true
    private var sendHeartbeat = false



    /**
     * 默认30s
     */
    private var socketTimeOut = 30

    private var packetLength = 0
    private const val TAIL_LEN = 2


    fun setSendHeartBeat(isSend: Boolean) {
        sendHeartbeat = isSend
    }


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
        if (boolean) {
            defaultHeadData = intToBytes(version)
        }
//        if (boolean) {
//            val verLenB = intToBytes(version)
//            val fixed = byteArrayOf(0x1, 0x2)
//            defaultHeadData = byteMerger(verLenB, fixed)
//            defaultTailData = TAIL_PACKET.toByteArray(Charsets.UTF_8)
//            //包头 + 包尾 最小长度
//            headPacketLength = defaultHeadData!!.size
//            tailPacketLength = defaultTailData!!.size
//            packetLength = headPacketLength + tailPacketLength
//        }
        return this
    }


    fun getDefaultHeadPacket(size: Int): ByteArray {
        return byteMerger((defaultHeadData ?: ByteArray(0)), intToBytes(size))
    }


}