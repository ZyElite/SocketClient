package com.zy.socket.imp

import android.annotation.SuppressLint
import com.zy.socket.callback.SocketConfig
import com.zy.socketclient.socket.SocketClient
import com.zy.socketclient.socket.SocketPacketConfig
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

/**
 *
 */
object SocketConfigImp : SocketConfig {

    /**
     *每隔30s发送一次心跳
     */
    override fun Heartbeat() {
        Observable.interval(0, SocketPacketConfig.getTimeOut(), TimeUnit.SECONDS)
                .subscribe {
                    //发送心跳
                    SocketClient.send(SocketPacketConfig.getHeartBeat() ?: ByteArray(0))
                }
    }
}