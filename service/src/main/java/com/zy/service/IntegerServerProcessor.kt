package com.zy.service

import org.smartboot.socket.MessageProcessor
import org.smartboot.socket.StateMachineEnum
import org.smartboot.socket.transport.AioSession
import java.io.IOException

class IntegerServerProcessor : MessageProcessor<Int> {
    override fun process(session: AioSession<Int>, msg: Int) {
        println("receive data from client: $msg ")
        try {
            session.write(msg)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun stateEvent(session: AioSession<Int>?, stateMachineEnum: StateMachineEnum?, throwable: Throwable?) {
        when (stateMachineEnum) {
            StateMachineEnum.NEW_SESSION -> {
                println("连接已建立并构建Session对象")
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
}