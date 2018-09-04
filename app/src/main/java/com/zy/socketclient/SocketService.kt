package com.zy.socketclient

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream


class SocketService : Service() {
    private val mList = ArrayList<Socket>()
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread(true, false, classLoader, "service", 1) {
            socketService()
        }.start()

        return super.onStartCommand(intent, flags, startId)
    }


    fun socketService() {
        //服务端在20006端口监听客户端请求的TCP连接
        val server = ServerSocket(20006)
        val f = true
        while (f) {
            //等待客户端的连接，如果没有获取连接
            var client = server.accept()
            mList.add(client);
            println("与客户端连接成功！")
            try {
                //获取Socket的输出流，用来向客户端发送数据
                val out = PrintStream(client.getOutputStream())
                //获取Socket的输入流，用来接收从客户端发送过来的数据
                val buf = BufferedReader(InputStreamReader(client.getInputStream()))
                var flag = true
                while (flag) {
                    //接收从客户端发送过来的数据
                    val str = buf.readLine()
                    if (str == null || "" == str) {
                        flag = false
                    } else {
                        if ("bye" == str) {
                            flag = false
                        } else {
                            //将接收到的字符串前面加上echo，发送到对应的客户端
                            out.println("echo:" + str!!)
                        }
                    }
                }
                out.close()
                client.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        server.close()
    }

}
