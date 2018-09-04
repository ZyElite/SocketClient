package com.zy.socketclient

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.Socket
import java.net.SocketTimeoutException
import kotlin.concurrent.thread


class ClientService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread(true, false, classLoader, "service", 1) {
            startClient()
        }.start()
        return super.onStartCommand(intent, flags, startId)
    }

    fun startClient() {
        //客户端请求与本机在20006端口建立TCP连接
        val client = Socket("127.0.0.1", 20006)
        client.soTimeout = 10000
        //获取键盘输入
        val input = BufferedReader(InputStreamReader(System.`in`))
        //获取Socket的输出流，用来发送数据到服务端
        val out = PrintStream(client.getOutputStream())
        //获取Socket的输入流，用来接收从服务端发送过来的数据
        val buf = BufferedReader(InputStreamReader(client.getInputStream()))
        var flag = true
        while (flag) {
            print("输入信息：")
            val str = input.readLine()
            //发送数据到服务端
            out.println(str)
            if ("bye" == str) {
                flag = false
            } else {
                try {
                    //从服务器端接收数据有个时间限制（系统自设，也可以自己设置），超过了这个时间，便会抛出该异常
                    val echo = buf.readLine()
                    println(echo)
                } catch (e: SocketTimeoutException) {
                    println("Time out, No response")
                }

            }
        }
        input.close()
        if (client != null) {
            //如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭
            client.close() //只关闭socket，其关联的输入输出流也会被关闭
        }
    }
}
