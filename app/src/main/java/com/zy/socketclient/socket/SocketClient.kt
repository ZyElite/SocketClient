package com.zy.socketclient.socket

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.zy.socketclient.expand.isRun
import java.io.IOException
import java.net.Socket
import java.util.concurrent.*


object SocketClient {
    private const val SEND_DATA_THREAD = "SendDataThread"
    private var socket: Socket? = null
    private var basket: LinkedBlockingQueue<ByteArray> = LinkedBlockingQueue<ByteArray>()
    private var sendThread: Thread? = null
    private var receiveThread: Thread? = null
    private var socketThread: Thread? = null
    private var mEnqueuePacketExecutor: ExecutorService? = null

    fun get(): Socket? = socket
    fun queue(): LinkedBlockingQueue<ByteArray> = basket
    @Throws(InterruptedException::class)
    fun send(byteArray: ByteArray) {
        val pack = byteMerger(getHeadData(byteArray.size), byteArray)
        basket.put(pack)
    }

    /**
     * 0-4   版本号
     * 4-8   总长度
     * 8-12  内容长度
     */
    private fun getHeadData(bodyLen: Int): ByteArray {
        val verLenB = intToBytes(1)
        Log.e("shuchu1", "$verLenB")
        val verAndBodyB = intToBytes(verLenB.size + bodyLen)
        val bodyLenB = intToBytes(bodyLen)
        return byteMerger(byteMerger(verLenB, verAndBodyB), bodyLenB)
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


    private fun byteMerger(first: ByteArray, second: ByteArray): ByteArray {
        val third = ByteArray(first.size + second.size)
        System.arraycopy(first, 0, third, 0, first.size)
        System.arraycopy(second, 0, third, first.size, second.size)
        return third
    }

    /**
     * connect socket
     */
    fun connect() {
        synchronized(this) {
            if (socket == null) {
                isRun()
                socketThread = Thread {
                    createClient()
                }
                socketThread?.start()
                sendThread = Thread(SendThread())
                sendThread?.start()
                receiveThread = Thread(ReceiveThread())
                receiveThread?.start()
                mEnqueuePacketExecutor = Executors.newSingleThreadExecutor { r -> Thread(r, SEND_DATA_THREAD) }
            }
        }
    }

    private fun createClient() {
        try {
            socket = Socket("192.168.98.110", 10010)
            socket?.keepAlive = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}