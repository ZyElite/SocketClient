package com.zy.socketclient.socket


/**
 * @des SendThread
 */
class SendThread : Runnable {
    override fun run() {
        try {
            while (SocketClient.queue().take().apply {
                        val outputStream = SocketClient.get()?.getOutputStream()
                        outputStream?.write(this)
                        outputStream?.flush()
                    } != null) {
            }
        } catch (e: InterruptedException) {
            println("发送线程已经关闭了")
        }
    }

}