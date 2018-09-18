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


    /**
     * 粘包处理
     *
     * @param recvLen 读取到的长度
     * @param receiveData 读取到的数据
     */
    @Throws(Exception::class)
    fun receivedPackage(recvLen: Int, receiveData: ByteArray) {
        //将收到的数据copy至缓冲区
        System.arraycopy(receiveData, 0, buffSequencePackage, sequenceLen, recvLen)
        //记录缓冲区使用的长度
        sequenceLen += recvLen
        if (sequenceLen < packetLength) {
            //如果数据长度小于默认包头长度 数据不完整
            return
        }
        //定义body
        var body = mutableListOf<Byte>()
        //临时长度
        //缓存区长度大于等于下一包的长度，说明缓冲区里还有完整的一包数据
        //如果缓存区的长度大于最小包长度 说明还有数据
        while (sequenceLen > packetLength) {
            //包头
            var head = Arrays.copyOfRange(buffSequencePackage, 0, headPacketLength)

            //处理包体
            var tailStr = headPacketLength
            while ((Arrays.copyOfRange(buffSequencePackage, headPacketLength, tailPacketLength)
                            ?: ByteArray(0)).contentEquals(defaultTailData!!)) {
                //
            }


            //判断任务是否结束。如果网络不佳或程序没有及时处理数据导致网络中的数据缓存过多，
            // 一次read读取数据就会将read的ReceiveData沾满，如果read的ReceiveData长度定义很大，
            // 将这些数据copy至缓存区，则这个循环会执行一段时间， 如果没有这层判断，即使任务结束，这个循环还在执行。
//            if (!AdapterManager.getInstance().isFlag()) {
//                return
//            }
            //取出第一包数据分析
            val siglePackage = Arrays.copyOfRange(buffSequencePackage, 0, siglePackageLen)
            //AnalyseReceivedPackage(siglePackage)
            //剔除缓冲区第一包数据
            val temp = Arrays.copyOfRange(buffSequencePackage, siglePackageLen, sequenceLen)
            //记录缓冲区usedlength
            sequenceLen = sequenceLen - siglePackageLen
            //清空缓存区
            //  resumeSequence()
            //将剩余数据copy至缓冲区，从头部开始
            System.arraycopy(temp, 0, buffSequencePackage, 0, temp.size)
            //再次进行验证，剩余的长度是否能提取出下一包数据的长度
            if (sequenceLen < 10) {
                return
            }
            //提取下一包数据长度
            //   lentemp = buffSequencePackage[1] - '0'
            //   siglePackageLen = Integer.parseInt(String(Arrays.copyOfRange(buffSequencePackage, 2, lentemp + 2))) + 2 + lentemp
        }
    }
}