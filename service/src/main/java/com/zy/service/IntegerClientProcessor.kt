package com.zy.service

import org.smartboot.socket.MessageProcessor
import org.smartboot.socket.NetMonitor
import org.smartboot.socket.StateMachineEnum
import org.smartboot.socket.transport.AioSession
import java.io.IOException
import java.lang.NullPointerException

class IntegerClientProcessor : MessageProcessor<Int>, NetMonitor<Int> {

    private var mSession: AioSession<Int>? = null

    override fun readMonitor(session: AioSession<Int>, readSize: Int) {

    }

    override fun writeMonitor(session: AioSession<Int>, writeSize: Int) {

    }

    override fun process(session: AioSession<Int>, msg: Int) {
        println("receive data from service: $msg ")
    }

    override fun stateEvent(session: AioSession<Int>?, stateMachineEnum: StateMachineEnum?, throwable: Throwable?) {
        when (stateMachineEnum) {
            StateMachineEnum.NEW_SESSION -> {
                println("连接已建立并构建Session对象")
                for (i in 0..100)
                    session?.write(i)
            }
            StateMachineEnum.SESSION_CLOSED -> {
                println("会话关闭成功。")
            }
            StateMachineEnum.PROCESS_EXCEPTION -> {
                println("业务处理异常。")
            }
            else -> {
                println("其他狀態")
            }
        }
    }

    fun getSession(): AioSession<Int> = mSession ?: throw NullPointerException()
}