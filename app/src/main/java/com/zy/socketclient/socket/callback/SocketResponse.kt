package com.zy.socketclient.socket.callback

import com.zy.socketclient.socket.SocketClient
import java.net.Socket

/**
 *
 */
interface SocketResponse {
    fun onConnected()
    fun onDisconnected()
    fun onResponse(str: String)
}