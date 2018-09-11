package com.zy.socketclient.socket

import com.zy.socketclient.expand.isRun
import java.io.IOException
import java.net.Socket

class SocketClient private constructor() {

    private var socket: Socket? = null

    /**
     * connect socket
     */
    fun connect() {
        Thread {
            //客户端请求与本机在10010端口建立TCP连接
            createClient()
        }.start()

    }

    fun createClient() {
        try {
            socket = Socket("192.168.98.110", 10010)
            socket?.keepAlive = true
            val inputStream = socket?.getInputStream()
            var buffer = ByteArray(1024)
            var len: Int
            do {
                len = inputStream?.read(buffer)!!
                val data = String(buffer, 0, len)
            } while (len != -1)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        var client: SocketClient? = null
            get() {
                if (client == null) {
                    isRun()
                    synchronized(this) {
                        client = SocketClient()
                    }
                }
                return client
            }
    }
}