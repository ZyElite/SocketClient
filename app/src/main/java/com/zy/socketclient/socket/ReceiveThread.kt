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
                    val result = mutableListOf<Byte>()
                    var data: Int = -1

                    //            var byteArray = ByteArray(1024)
                    while ({ data = SocketClient.get()?.getInputStream()?.read() ?: -1;data }() != -1) {
                        result.add(data.toByte())
                        Log.e("ReceiveThread", "end ${result.size}")
                    }

            }

//            var byteArray = ByteArray(1024)
//            while (SocketClient.get()?.getInputStream()?.read(byteArray)?.apply {
//                        val content = String(byteArray, 0, this)
//                        save {
//                            val createObject = it.createObject(Message::class.java)
//                            createObject.date = System.currentTimeMillis().toString()
//                            createObject.id = 2
//                            createObject.name = "测试二"
//                            createObject.content = content + "转发"
//                        }
//                    } != -1) {
//                byteArray = ByteArray(1024)
//            }
        } catch (e: InterruptedException) {
            println("接收线程已经关闭了")
        }
    }
}

//                while (true) {
//                    val inputStream = SocketClient.get()?.getInputStream()
//                    inputStream?.let {
//                        val byteArray = ByteArray(1024)
//                        var read: Int
//                        do {
//                            read = inputStream.read(byteArray)
//                            val content = String(byteArray, 0, read)
//                            save {
//                                val createObject = it.createObject(Message::class.java)
//                                createObject.date = System.currentTimeMillis().toString()
//                                createObject.id = 2
//                                createObject.name = "对方"
//                                createObject.content = content + "转发"
//                            }
//                        } while (read != -1)
//                    }
//        }
