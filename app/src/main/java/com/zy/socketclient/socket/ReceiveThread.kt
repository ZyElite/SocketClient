package com.zy.socketclient.socket

import android.util.Log
import com.zy.socketclient.expand.isRun
import com.zy.socketclient.expand.save
import com.zy.socketclient.model.Message

/**
 * @des ReceiveThread
 */
class ReceiveThread : Runnable {
    override fun run() {
        try {
            while (true) {
                val inputStream = SocketClient.get()?.getInputStream()
                inputStream?.let {
                    val byteArray = ByteArray(1024)
                    var read: Int
                    do {
                        read = inputStream.read(byteArray)
                        val content = String(byteArray, 0, read)
                        save {
                            val createObject = it.createObject(Message::class.java)
                            createObject.date = System.currentTimeMillis().toString()
                            createObject.id = 2
                            createObject.name = "对方"
                            createObject.content = content + "转发"
                        }
                    } while (read != -1)
                }
            }
        } catch (e: InterruptedException) {
            println("接收线程已经关闭了")
        }
    }
}