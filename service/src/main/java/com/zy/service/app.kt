package com.zy.service


fun main(args: Array<String>) {
    val serverThread = ServerThread()
    Thread(serverThread).start()
}