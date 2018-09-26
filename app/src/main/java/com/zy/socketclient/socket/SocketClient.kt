package com.zy.socketclient.socket

import com.zy.socketclient.expand.isRun
import com.zy.socketclient.socket.SocketPacketConfig.getDefaultHeadPacket
import com.zy.socketclient.socket.callback.SocketResponse
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
    private var result: SocketResponse? = null


    fun get(): Socket? = socket

    fun queue(): LinkedBlockingQueue<ByteArray> = basket

    @Throws(InterruptedException::class)
    fun send(byteArray: ByteArray) {
        val pack = byteMerger(getDefaultHeadPacket(byteArray.size), byteArray)
        basket.put(pack)
    }

    fun getRes(): SocketResponse? = result

    /**
     * connect socket
     */
    fun connect() {
        synchronized(this) {
            if (socket == null) {
                socketThread = Thread {
                    createClient()
                }
                socket?.isConnected?.apply {
                    result?.onConnected()
                    socketThread?.start()
                    sendThread = Thread(SendThread())
                    sendThread?.start()
                    receiveThread = Thread(ReceiveThread())
                    receiveThread?.start()
                    mEnqueuePacketExecutor = Executors.newSingleThreadExecutor { r -> Thread(r, SEND_DATA_THREAD) }
                } ?: result?.onDisconnected()
            }
        }
    }

    /**
     * close socket
     */
    fun close() {
        socketThread?.interrupt()
        sendThread?.interrupt()
        receiveThread?.interrupt()
        socket = null
    }

    private fun createClient() {
        try {
            socket = Socket("192.168.98.110", 10010)
            socket?.keepAlive = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     *注册socket回调
     */
    fun registerRes(response: SocketResponse) {
        result = response
    }

}