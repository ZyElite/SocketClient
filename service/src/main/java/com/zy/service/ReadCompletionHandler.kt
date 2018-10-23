package com.zy.service

import java.nio.channels.CompletionHandler

class ReadCompletionHandler<T> : CompletionHandler<Int, Attachment<T>> {

    override fun completed(result: Int, attachment: Attachment<T>) {
        println("ReadCompletionHandler $result   attachment：${String(attachment.getReadBuffer()?.array()!!)}")
    }

    override fun failed(exc: Throwable, attachment: Attachment<T>) {
        println("ReadCompletionHandler ${exc.message}")
    }
}
