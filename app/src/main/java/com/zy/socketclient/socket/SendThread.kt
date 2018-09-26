package com.zy.socketclient.socket

import com.zy.socketclient.expand.log


/**
 * @des SendThread
 */
class SendThread : Runnable {
    override fun run() {
        try {
            var data: ByteArray? = null
            while (SocketClient.get()?.isConnected == true && SocketClient.get()?.isOutputShutdown != true && { data = SocketClient.queue().take();data }() != null) {
                val outputStream = SocketClient.get()?.getOutputStream()
                outputStream?.write(data)
                outputStream?.flush()
            }
            SocketClient.getRes()?.onDisconnected()
        } catch (e: InterruptedException) {
            println("发送线程已经关闭了")
        }
    }

}