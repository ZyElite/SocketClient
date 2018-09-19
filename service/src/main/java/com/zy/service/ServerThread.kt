package com.zy.service

import java.io.IOException
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import kotlin.experimental.and
import kotlin.experimental.or

class ServerThread : Runnable {

    private val port = 10010
    private var isExit = false
    private var server: ServerSocket? = null
    private val clientList = mutableMapOf<String, Socket>()


    init {
        try {
            server = ServerSocket(port)
            println("启动服务成功port:$port")
        } catch (e: IOException) {
            println("启动server失败，错误原因：${e.message}")
        }

    }

    override fun run() {
        try {
            while (!isExit) {
                // 进入等待环节
                println("等待手机的连接... ... ")
                val socket = server!!.accept()
                // 获取手机连接的地址及端口号
                val address = socket.remoteSocketAddress.toString()
                println("连接成功，连接的手机为：$address")
                Thread(object : Runnable {
                    override fun run() {
                        try {
                            // 单线程索锁
                            synchronized(this) {
                                // 放进到Map中保存
                                clientList.put(address, socket)
                            }
                            // 定义输入流
                            while (true) {
                                val bytes = mutableListOf<Byte>()
                                var data: Int = -1
                                var bLength: Int = -1
                                var body = 0
                                while ({ data = socket.getInputStream().read();data }() != -1) {
                                    bytes.add(data.toByte())
                                    if (bytes.size == 8) {
                                        //包头
                                        bLength = bytesToInt(bytes.subList(4, 8).toByteArray())
                                        println("length：$bLength")
                                    }
                                    if (bLength != -1) {
                                        if (body == bLength) {
                                            val byteArray = bytes.toByteArray()
                                            val string = String(byteArray, 8, bLength)
                                            println("收到的信息为：$string")
                                            sendMsgAll(byteArray)
                                            bytes.clear()
                                            break
                                        }
                                        body++
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            println("错误信息为：" + e.message)
                        } finally {
                            synchronized(this) {
                                println("关闭链接：$address")
                                clientList.remove(address)
                            }
                        }
                    }
                }).start()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

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


    fun Stop() {
        isExit = true
        if (server != null) {
            try {
                server!!.close()
                println("已关闭server")
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    fun sendMsgAll(msg: ByteArray): Boolean {
        try {
            for (socket in clientList.values) {
                val outputStream = socket.getOutputStream()
                outputStream.write(msg)
                outputStream.flush()
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }


    fun sendMsgAll(msg: String): Boolean {
        try {
            for (socket in clientList.values) {
                val outputStream = socket.getOutputStream()
                outputStream.write(msg.toByteArray(Charsets.UTF_8))
                outputStream.flush()
                println("转发消息:  $msg")
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun ShowDown() {
        clientList.forEach { s, socket ->
            try {
                socket.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        this.Stop()
        clientList.clear()
    }
}