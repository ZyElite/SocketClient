package com.zy.service

import java.nio.channels.CompletionHandler

class ReadCompletionHandler<T> : CompletionHandler<Int, Conversation<T>> {

    override fun completed(result: Int, attachment: Conversation<T>) {
        println("ReadCompletionHandler $result")
    }

    override fun failed(exc: Throwable, attachment: Conversation<T>) {
        println("ReadCompletionHandler ${exc.message}")
    }
}
