package com.zy.socketclient

import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.View
import com.zy.socketclient.R.id.sendView
import com.zy.socketclient.expand.log
import com.zy.socketclient.expand.query
import com.zy.socketclient.expand.save
import com.zy.socketclient.model.Message
import com.zy.socketclient.socket.SocketClient
import com.zy.socketclient.socket.SocketPacketConfig
import com.zy.socketclient.socket.callback.SocketResponse
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.net.Socket
import io.realm.RealmChangeListener
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ChatAdapter()
        recycle.adapter = adapter
//        val results = Realm.getDefaultInstance().where(Message::class.java).findAllAsync()
//        results.addChangeListener(RealmChangeListener<RealmResults<Message>> {
////            adapter.replace(it)
////            recycle.scrollToPosition(it.size - 1)
//        })
        adapter.replace(query<Message> {
            val results = it.where(Message::class.java).findAllAsync()
            results.addChangeListener(RealmChangeListener<RealmResults<Message>> {
                adapter.replace(it)
                recycle.scrollToPosition(it.size - 1)
            })
            results
        })
        SocketPacketConfig.setDefaultPacket(true)
        SocketClient.registerRes(object : SocketResponse {
            override fun onConnected() {
                log("socket 连接已连接")
            }

            override fun onDisconnected() {
                log("socket 连接关闭了")
            }

            override fun onResponse(res: String) {
                save {
                    val createObject = it.createObject(Message::class.java)
                    createObject.date = System.currentTimeMillis().toString()
                    createObject.id = 2
                    createObject.name = "测试二"
                    createObject.content = res + "转发"
                }
            }
        })
        SocketClient.connect()
        sendView.setSendListener(View.OnClickListener {
            //send
            val content = sendView.getText().toString()
            save {
                val createObject = it.createObject(Message::class.java)
                createObject.date = System.currentTimeMillis().toString()
                createObject.id = 1
                createObject.name = "测试一"
                createObject.content = content
            }
            SocketClient.send(content.toByteArray(Charsets.UTF_8))
        })
    }


}
