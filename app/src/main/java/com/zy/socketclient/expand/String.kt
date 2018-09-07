package com.zy.socketclient.expand

import android.text.TextUtils
import android.util.Log

private const val TAG = "ZyElite"

fun String.log() {
    Log.e(generateTag(), this)
}

private fun generateTag(): String {
    val caller = Throwable().stackTrace[2]
    var tag = "%s.%s(L:%d)"
    var callerClazzName = caller.className
    callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1)
    tag = String.format(tag, callerClazzName, caller.methodName, caller.lineNumber)
    tag = if (TextUtils.isEmpty(tag)) tag else "$TAG:$tag"
    return tag
}
