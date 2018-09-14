package com.zy.service

import java.io.IOException
import java.net.ServerSocket
import java.net.Socket
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

                            while (true){

                                val bytes = mutableListOf<Byte>()
                                var data: Int = -1
                                var bLength: Int = -1
                                while ({ data = socket.getInputStream().read();data }() != -1) {
                                    println("server：")
                                    bytes.add(data.toByte())
                                    if (bytes.size == 4) {
                                        //版本信息
                                        val version = bytesToInt(bytes.toByteArray())
                                        println("version：$version")
                                    }
                                    if (bytes.size == 8) {
                                        val all = ByteArray(4)
                                        for (byte in 4 until 8) {
                                            all[byte - 4] = bytes[byte]
                                        }
                                        val length = bytesToInt(all)
                                        println("length：$length")
                                    }

                                    if (bytes.size == 12) {
                                        val body = ByteArray(4)
                                        for (byte in 9 until 12) {
                                            body[byte - 8] = bytes[byte]
                                        }
                                        bLength = bytesToInt(body)
                                        println("body：$bLength")
                                    }
                                    if (bytes.size == (12 + bLength)) {
                                        val string = String(bytes.toByteArray(), 12, bLength)
                                        println("收到的信息为：$string")
                                        break
                                    }
                                }
                            }

//                            println("server：")
//                            val inputStream = socket.getInputStream()
//                            val buffer = ByteArray(1024)
//                            var len: Int
//                            do {
//                                len = inputStream.read(buffer)
//                                val text = String(buffer, 0, len)
//                                println("收到的数据为：$text")
//                                // 在这里群发消息
//                                sendMsgAll(buffer)
//                            } while (len != -1)
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