package com.zy.socketclient

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.Socket


class ServerThread(client: Socket) : Runnable {
    private var mClient = client

    override fun run() {
        try {
            //获取Socket的输出流，用来向客户端发送数据
            val out = PrintStream(mClient!!.getOutputStream())
            //获取Socket的输入流，用来接收从客户端发送过来的数据
            val buf = BufferedReader(InputStreamReader(mClient!!.getInputStream()))
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
            mClient!!.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}