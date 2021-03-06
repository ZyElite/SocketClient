package com.zy.socketclient.socket

import android.util.Log
import com.zy.socketclient.expand.log
import com.zy.socketclient.socket.utils.SocketHelp.bytesToInt
import java.net.SocketTimeoutException

/**
 * @des ReceiveThread
 */
class ReceiveThread : Runnable {

    override fun run() {
        try {
            while (SocketClient.get()?.isConnected == true && SocketClient.get()?.isInputShutdown != true) {
                val bytes = mutableListOf<Byte>()
                var data: Int = -1
                var bLength: Int = -1
                var body = 0
                var headLength = 8
                if (!SocketPacketConfig.isAddDefaultHead()) {
                    headLength = SocketPacketConfig.getCustomizeReceive()?.headLength() ?: 0
                }
                while ({ data = SocketClient.get()?.getInputStream()?.read() ?: -1;data }() != -1) {
                    bytes.add(data.toByte())
                    if (bytes.size == headLength) {
                        bLength = if (SocketPacketConfig.isAddDefaultHead()) {
                            bytesToInt(bytes.subList(4, 8).toByteArray())
                        } else {
                            SocketPacketConfig.getCustomizeReceive()?.bodyLength(bytes.toByteArray())
                                    ?: 0
                        }
                    }
                    if (bLength > 0) {
                        if (body == bLength) {
                            val string = String(bytes.toByteArray(), headLength, bLength)
                            SocketClient.getRes()?.onResponse(string)
                            bytes.clear()
                            break
                        }
                        body++
                    } else if (bLength == 0) {
                        log("接收到心跳包")
                        break
                    }
                }
            }
        } catch (e: InterruptedException) {
            SocketClient.getRes()?.onDisconnected("Socket ReceiveThread Interrupted")
        } catch (e: SocketTimeoutException) {
            //Socket  Read timed out
            SocketClient.getRes()?.onDisconnected("Socket Read timed out")
        } finally {
            //close all socket
            Log.e("ReceiveThread", "finally")
            SocketClient.close()
        }
    }
}

