package com.zy.service

import java.io.IOException
import java.net.ServerSocket
import java.net.Socket

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
                            val inputStream = socket.getInputStream()
                            val buffer = ByteArray(1024)
                            var len: Int
                            do {
                                len = inputStream.read(buffer)
                                val text = String(buffer, 0, len)
                                println("收到的数据为：$text")
                                // 在这里群发消息
                                sendMsgAll(text)
                            } while (len != -1)
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

    // 群发的方法
    fun sendMsgAll(msg: String): Boolean {
        try {
            for (socket in clientList.values) {
                val outputStream = socket.getOutputStream()
                outputStream.write(msg.toByteArray(charset("utf-8")))
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