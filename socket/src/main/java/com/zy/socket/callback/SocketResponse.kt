package com.zy.socketclient.socket.callback


/**
 *
 */
interface SocketResponse {
    fun onConnected()
    fun onDisconnected(str: String)
    fun onResponse(str: String)
}