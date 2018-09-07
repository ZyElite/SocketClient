package com.zy.socketclient

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zy.socketclient.expand.log
import com.zy.socketclient.expand.query
import com.zy.socketclient.expand.save
import com.zy.socketclient.model.Message
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.net.Socket
import io.realm.RealmChangeListener


class MainActivity : AppCompatActivity() {


    private var socket: Socket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ChatAdapter()
        recycle.adapter = adapter
        adapter.replace(query<Message> {
            val results = it.where(Message::class.java).findAllAsync()
            results.addChangeListener(RealmChangeListener<RealmResults<Message>> {
                adapter.replace(it)
                recycle.scrollToPosition(it.size - 1)
            })
            results
        })

        Thread {
            startClient()
        }.start()
//        thread(true, false, classLoader, "service", 1) {
//
//        }.start()
        sendView.setSendListener(View.OnClickListener {
            //send
            val content = sendView.getText().toString()
            save {
                val createObject = it.createObject(Message::class.java)
                createObject.date = System.currentTimeMillis().toString()
                createObject.id = 1
                createObject.name = "测试仪"
                createObject.content = content
            }
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
                save {
                    val createObject = it.createObject(Message::class.java)
                    createObject.date = System.currentTimeMillis().toString()
                    createObject.id = 2
                    createObject.name = "对方"
                    createObject.content = data + "转发"
                }
            } while (len != -1)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }



}
