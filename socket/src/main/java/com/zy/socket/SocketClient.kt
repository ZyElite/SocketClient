package com.zy.socketclient.socket

import com.zy.socket.imp.SocketConfigImp
import com.zy.socketclient.socket.SocketPacketConfig.getDefaultHeadPacket
import com.zy.socketclient.socket.callback.SocketResponse
import com.zy.socketclient.socket.utils.SocketHelp.byteMerger
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.*


object SocketClient {

    private const val SEND_DATA_THREAD = "SendDataThread"
    private var socket: Socket? = null
    private var basket: LinkedBlockingQueue<ByteArray> = LinkedBlockingQueue()
    private var sendThread: Thread? = null
    private var receiveThread: Thread? = null
    private var socketThread: Thread? = null
    private var mEnqueuePacketExecutor: ExecutorService? = null
    private var result: SocketResponse? = null


    fun get(): Socket? = socket

    fun queue(): LinkedBlockingQueue<ByteArray> = basket

    @Throws(InterruptedException::class)
    fun send(byteArray: ByteArray) {
        if (SocketPacketConfig.isAddDefaultHead()) {
            val pack = byteMerger(getDefaultHeadPacket(byteArray.size), byteArray)
            basket.put(pack)
        } else {
            basket.put(byteArray)
        }
    }

    fun getRes(): SocketResponse? = result


    /**
     * connect socket
     */
    fun connect() {
        synchronized(this) {
            close()
            if (socket == null) {
                socketThread = Thread {
                    createClient()
                }
                socketThread?.start()
                createConnect()
            }

        }
    }


    private fun sendHeartbeat() {
//        Observable.interval(0, SocketPacketConfig.get)
    }


    /**
     * 三秒之内 没有服务器建立连接 定义为连接失败
     */
    private fun createConnect() {
        var disposable: Disposable? = null
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .map { socket?.isConnected ?: false }
                .doOnNext {
                    if (it) {
                        sendThread = Thread(SendThread())
                        sendThread?.start()
                        receiveThread = Thread(ReceiveThread())
                        receiveThread?.start()
                        //mEnqueuePacketExecutor = Executors.newSingleThreadExecutor { r -> Thread(r, SEND_DATA_THREAD) }
                    }
                }
                .take(3)
                .subscribe(object : Observer<Boolean> {
                    override fun onNext(t: Boolean) {
                        if (t) {
                            result?.onConnected()
                            //发送心跳
                            SocketConfigImp.heartBeat()
                            disposable?.dispose()
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        result?.onDisconnected("Socket Read timed out")
                    }
                })
    }

    /**
     * close socket
     */
    fun close() {
        socket?.shutdownInput()
        socket?.shutdownOutput()
        socket?.close()
        socket = null
        socketThread?.interrupt()
        sendThread?.interrupt()
        receiveThread?.interrupt()
    }

    private fun createClient() {
        try {
            socket = Socket()
            socket?.connect(InetSocketAddress(SocketPacketConfig.getAddress(), SocketPacketConfig.getPort()), SocketPacketConfig.getConnTimeOut())
            socket?.keepAlive = true
            socket?.soTimeout = (SocketPacketConfig.getTimeOut().toInt() * 1000)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     *注册socket回调
     */
    fun registerRes(response: SocketResponse) {
        result = response
    }



}


