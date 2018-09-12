package com.zy.socketclient.socket

import android.util.Log
import com.zy.socketclient.expand.isRun
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

/**
 * @des SendThread
 */
class SendThread : Runnable {
    private val queue = SocketClient.queue()
    override fun run() {
        try {
            while (true) {
                Log.e("SendThread", "发送线程开启了")
                if (queue.isNotEmpty()) {
                    val outputStream = SocketClient.get()?.getOutputStream()
                    outputStream?.write(queue?.take())
                    outputStream?.flush()
                }
            }
        } catch (e: InterruptedException) {
            println("发送线程已经关闭了")
        }
    }


}