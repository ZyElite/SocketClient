package com.zy.socketclient.socket

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.zy.socketclient.expand.isRun
import java.io.IOException
import java.net.Socket
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue


object SocketClient {

    private var socket: Socket? = null
    private var basket: BlockingQueue<ByteArray> = LinkedBlockingQueue<ByteArray>()
    private var sendThread: Thread? = null
    private var receiveThread: Thread? = null
    private var socketThread: Thread? = null

    fun get(): Socket? = socket

    fun queue(): BlockingQueue<ByteArray> = basket


    @Throws(InterruptedException::class)
    fun send(byteArray: ByteArray) {
        Log.e("send", "${sendThread?.isInterrupted} == ${receiveThread?.isInterrupted} ")
        basket.put(byteArray)
    }

    /**
     * connect socket
     */
    fun connect() {
        synchronized(this) {
            if (socket == null) {
                isRun()
                socketThread = Thread {
                    //客户端请求与本机在10010端口建立TCP连接
                    createClient()
                }
                socketThread?.start()
                sendThread = Thread(SendThread())
                sendThread?.start()
                receiveThread = Thread(ReceiveThread())
                receiveThread?.start()
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