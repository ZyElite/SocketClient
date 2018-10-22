package com.zy.socketclient

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zy.socket.callback.SocketCustomizeReceive
import com.zy.socketclient.expand.log
import com.zy.socketclient.expand.query
import com.zy.socketclient.expand.save
import com.zy.socketclient.model.Message
import com.zy.socketclient.socket.SocketClient
import com.zy.socketclient.socket.SocketPacketConfig
import com.zy.socketclient.socket.SocketPacketConfig.setSocketAddress
import com.zy.socketclient.socket.callback.SocketResponse
import com.zy.socketclient.socket.utils.SocketHelp
import com.zy.socketclient.socket.utils.SocketHelp.bytesToInt
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import io.realm.RealmChangeListener
import java.io.InputStream
import java.nio.ByteBuffer


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ChatAdapter()
        recycle.adapter = adapter
        adapter.replace(query<Message> { it ->
            val results = it.where(Message::class.java).findAllAsync()
            results.addChangeListener(RealmChangeListener<RealmResults<Message>> {
                adapter.replace(it)
                recycle.scrollToPosition(it.size - 1)
            })
            results
        })
        SocketPacketConfig.setDefaultPacket(true)
                //是否发送心跳 默人false
                .setSendHeartBeat(true)
                //设置套接字地址
                .setSocketAddress("192.168.98.110", 10010, 10000)
                //设置自定义接收解析方式
                .setCustomizeReceive(object : SocketCustomizeReceive {

                    override fun headLength(): Int {
                        //返回包协议头的长度
                        return 8
                    }

                    override fun bodyLength(head: ByteArray): Int {
                        //解析包头返回包体的长度
                        return SocketHelp.bytesToInt(head)
                    }

                })

        SocketClient.registerRes(object : SocketResponse {
            override fun onConnected() {
                log("socket 连接已连接")
            }

            override fun onDisconnected(str: String) {
                log(str)
            }

            override fun onResponse(str: String) {
                save {
                    val createObject = it.createObject(Message::class.java)
                    createObject.date = System.currentTimeMillis().toString()
                    createObject.id = 2
                    createObject.name = "测试二"
                    createObject.content = str + "转发"
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
