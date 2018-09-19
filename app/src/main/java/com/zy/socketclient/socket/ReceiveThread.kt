package com.zy.socketclient.socket

import android.util.Log
import com.zy.socketclient.expand.isRun
import com.zy.socketclient.expand.save
import com.zy.socketclient.model.Message
import com.zy.socketclient.socket.utils.SocketHelp.bytesToInt

/**
 * @des ReceiveThread
 */
class ReceiveThread : Runnable {

    override fun run() {
        try {
            while (true) {
                while (true) {
                    val bytes = mutableListOf<Byte>()
                    var data: Int = -1
                    var bLength: Int = -1
                    var body = 0
                    while ({ data = SocketClient.get()?.getInputStream()?.read() ?: -1;data }() != -1) {
                        bytes.add(data.toByte())
                        if (bytes.size == 8) {
                            //包头
                            bLength = bytesToInt(bytes.subList(4, 8).toByteArray())
                            println("length：$bLength")
                        }
                        if (bLength != -1) {
                            if (body == bLength) {
                                val string = String(bytes.toByteArray(), 8, bLength)
                                println("收到的信息为：$string")
                                save {
                                    val createObject = it.createObject(Message::class.java)
                                    createObject.date = System.currentTimeMillis().toString()
                                    createObject.id = 2
                                    createObject.name = "测试二"
                                    createObject.content = string + "转发"
                                }
                                bytes.clear()
                                break
                            }
                            body++
                        }

                    }
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
