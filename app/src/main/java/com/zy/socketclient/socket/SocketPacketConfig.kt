package com.zy.socketclient.socket

import com.zy.socketclient.socket.utils.SocketHelp.byteMerger
import com.zy.socketclient.socket.utils.SocketHelp.intToBytes
import java.util.*

/**
 * 发送消息 信息包处理类
 */
object SocketPacketConfig {

    private var version: Int = 0
    private var headData: ByteArray? = null
    private var tailData: ByteArray? = null
    private var headPacketLength = 0
    private var tailpacketLength = 0
    private var heartbeat: ByteArray? = null

    private var defaultHeadData: ByteArray? = null

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
        tailpacketLength = len
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
     * 0-4   版本号
     * 4-8   总长度
     * 8-12  内容长度
     * 版本号 + （版本号长度+ 内容长度）+ 内容长度
     */
    fun setDefaultHeadPacket(bodyLen: Int): SocketPacketConfig {
        val verLenB = intToBytes(version)
        val verAndBodyB = intToBytes(verLenB.size + bodyLen)
        val bodyLenB = intToBytes(bodyLen)
        defaultHeadData = byteMerger(byteMerger(verLenB, verAndBodyB), bodyLenB)
        return this
    }

    fun getDefauleHeadPacket(): ByteArray? {
        return defaultHeadData
    }

}