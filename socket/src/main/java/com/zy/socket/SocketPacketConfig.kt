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
    private var heartbeat: ByteArray? = ByteArray(10)
    private var defaultHeadData: ByteArray? = null
    private var defaultTailData: ByteArray? = null
    private var addPacket = true

    private var sendHeartbeat = false


    /**
     * 默认超时时间30s
     */
    private var socketTimeOut = 30L

    private var packetLength = 0
    private const val TAIL_LEN = 2


    /**
     * 设置是否发送心跳包
     * 默认是 false
     */
    fun setSendHeartBeat(isSend: Boolean) {
        sendHeartbeat = isSend
    }

    /**
     * 是否允许发送心跳包
     */
    fun isSendHeartBeat(): Boolean = sendHeartbeat


    /**
     *设置超时时间
     * 默认时间是30s
     */
    fun setTimeOut(time: Long) {
        socketTimeOut = time
    }

    /**
     *获取socket超时时间
     */
    fun getTimeOut(): Long = socketTimeOut


    /**
     * 设置socket版本号
     */
    fun setSocketVer(ver: Int): SocketPacketConfig {
        version = ver
        return this
    }


    /**
     * 设置默认包头包尾
     * 0-3   版本号
     * 4-7   数据长度
     * 版本号 +  固定长度
     */
    fun setDefaultPacket(boolean: Boolean): SocketPacketConfig {
        addPacket = boolean
        if (boolean) {
            defaultHeadData = intToBytes(version)
            headPacketLength = defaultHeadData?.size!!
        }

        return this
    }

    /**
     * 发送的时候
     * 版本号 +  数据长度
     */
    fun getDefaultHeadPacket(size: Int): ByteArray {
        return byteMerger(defaultHeadData ?: ByteArray(0), intToBytes(size))
    }


//    fun setHeadData(head: ByteArray): SocketPacketConfig {
//        if (headData == null) {
//            headData = head
//        }
//        return this
//    }
//
//    fun setTailData(tail: ByteArray): SocketPacketConfig {
//        if (tailData == null) {
//            tailData = tail
//        }
//        return this
//    }

//    fun setHeadPacketLength(len: Int): SocketPacketConfig {
//        headPacketLength = len
//        return this
//    }
//
//    fun setTailPacketLength(len: Int): SocketPacketConfig {
//        tailPacketLength = len
//        return this
//    }


//    fun setHeartBeat(heart: ByteArray): SocketPacketConfig {
//        heartbeat = heart
//        return this
//    }
//
//    fun getHeartBeat(): ByteArray? {
//        return heartbeat
//    }


}