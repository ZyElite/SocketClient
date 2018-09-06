package com.zy.socketclient

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zy.socketclient.expand.log
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.net.Socket
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {


    private var socket: Socket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycle.adapter = ChatAdapter()
        Thread {
            startClient()
        }.start()
//        thread(true, false, classLoader, "service", 1) {
//
//        }.start()
        sendView.setSendListener(View.OnClickListener {
            //send
            val content = sendView.getText().toString()
            content.log()
            Thread {
                val outputStream = socket?.getOutputStream()
                outputStream?.write(content.toByteArray(Charsets.UTF_8))
                outputStream?.flush()
            }.start()
        })

    }

    fun startClient() {
        //客户端请求与本机在10010端口建立TCP连接
        try {
            socket = Socket("192.168.98.110", 10010)
            socket?.keepAlive = true
            val inputStream = socket?.getInputStream()
            var buffer = ByteArray(1024)
            var len: Int
            do {
                len = inputStream?.read(buffer)!!
                val data = String(buffer, 0, len)
                data.log()
            } while (len != -1)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
