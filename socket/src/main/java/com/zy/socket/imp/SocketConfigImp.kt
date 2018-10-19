package com.zy.socket.imp

import android.annotation.SuppressLint
import com.zy.socket.callback.SocketConfig
import com.zy.socketclient.socket.SocketClient
import com.zy.socketclient.socket.SocketPacketConfig
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate
import java.util.concurrent.TimeUnit

/**
 *
 */
object SocketConfigImp : SocketConfig {

    /**
     * 默认每隔30s发送一次心跳
     * 请先设置 允许是否发送心跳包 默认不允许
     * {@link com.zy.socketclient.socket.SocketPacketConfig}
     */
    @SuppressLint("CheckResult")
    override fun heartBeat() {
        Observable.interval(0, SocketPacketConfig.getTimeOut(), TimeUnit.SECONDS)
                .filter { SocketPacketConfig.isSendHeartBeat() }
                .subscribe {
                    //发送心跳
                    SocketClient.queue().put(SocketPacketConfig.getDefaultHeadPacket(0))
                }
    }
}