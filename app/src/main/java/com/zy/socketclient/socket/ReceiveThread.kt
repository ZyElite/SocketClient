package com.zy.socketclient.socket

import android.util.Log
import com.zy.socketclient.expand.isRun
import com.zy.socketclient.expand.save
import com.zy.socketclient.model.Message

/**
 * @des ReceiveThread
 */
class ReceiveThread : Runnable {
    private val buffer = ByteArray(1024)
    override fun run() {
        //  while (true) {

        try {
            var len: Int = -1
            do {
                SocketClient.get()?.getInputStream()?.read(buffer)?.let {
                    Log.e("ReceiveThread", "接收线程开启了")
                    len = it
                    val data = String(buffer, 0, len)
                    save {
                        val createObject = it.createObject(Message::class.java)
                        createObject.date = System.currentTimeMillis().toString()
                        createObject.id = 2
                        createObject.name = "对方"
                        createObject.content = data + "转发"
                    }
                }

            } while (len != -1)
//                inputStream?.inputStream?.readBytes()?.let { it ->
//                    val content = String(it)
//                    save {
//                        val createObject = it.createObject(Message::class.java)
//                        createObject.date = System.currentTimeMillis().toString()
//                        createObject.id = 2
//                        createObject.name = "对方"
//                        createObject.content = content + "转发"
//                    }
//                }
        } catch (e: InterruptedException) {
            println("接收线程已经关闭了")
        }
    }
}