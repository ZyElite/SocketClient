package com.zy.socketclient.socket


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

        } catch (e: InterruptedException) {
            SocketClient.getRes()?.onDisconnected("Socket SendThread Interrupted")
        }
    }

}