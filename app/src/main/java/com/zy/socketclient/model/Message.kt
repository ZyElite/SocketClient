package com.zy.socketclient.model

import io.realm.RealmObject
import java.math.BigDecimal

/**
 * Message bean
 */
open class Message : RealmObject() {
    var date: String = ""
    var id: Int = 0
    var content: String = ""
    var name: String = ""


    override fun toString(): String {
        return "Message(date='$date', id=$id, content='$content', name='$name')"
    }
}