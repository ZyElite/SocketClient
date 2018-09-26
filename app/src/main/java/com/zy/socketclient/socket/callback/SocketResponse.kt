package com.zy.socketclient.socket.callback


/**
 *
 */
interface SocketResponse {
    fun onConnected()
    fun onDisconnected()
    fun onResponse(str: String)
}