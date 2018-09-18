package com.zy.socketclient.socket

import com.zy.socketclient.expand.isRun
import com.zy.socketclient.socket.SocketPacketConfig.getDefaultHeadPacket
import com.zy.socketclient.socket.utils.SocketHelp.byteMerger
import java.io.IOException
import java.net.Socket
import java.util.concurrent.*


object SocketClient {
    private const val SEND_DATA_THREAD = "SendDataThread"
    private var socket: Socket? = null
    private var basket: LinkedBlockingQueue<ByteArray> = LinkedBlockingQueue()
    private var sendThread: Thread? = null
    private var receiveThread: Thread? = null
    private var socketThread: Thread? = null
    private var mEnqueuePacketExecutor: ExecutorService? = null

    fun get(): Socket? = socket
    fun queue(): LinkedBlockingQueue<ByteArray> = basket
    @Throws(InterruptedException::class)
    fun send(byteArray: ByteArray) {
        val pack = byteMerger(getDefaultHeadPacket(byteArray.size), byteArray)
        basket.put(pack)
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