package com.zy.socketclient.expand

import android.util.Log
import io.realm.Realm
import io.realm.RealmResults

private const val TAG = "ZyElite"

/**
 * @desc Synchronous access
 * @thread create thread
 */
fun save(body: (realm: Realm) -> Unit) {
    val realm = Realm.getDefaultInstance()
    save({ realm.beginTransaction() }, {
        body(realm)
    }, { realm.commitTransaction() })
}


/**
 * @desc query
 * @thread create thread
 */
fun query(function: (realm: Realm) -> Unit) {
    function(Realm.getDefaultInstance())
}

/**
 * return Result set
 */

fun <T> query(function: (realm: Realm) -> RealmResults<T>): RealmResults<T> {
    return function(Realm.getDefaultInstance())
}

private fun save(start: () -> Unit, body: (realm: Realm) -> Unit, end: () -> Unit) {
    start()
    body(Realm.getDefaultInstance())
    end()
}

/**
 * 当前执行线程
 */
fun currentThreadName() {
    Log.e(TAG, Thread.currentThread().name)
}

/**
 * 是否执行
 */
fun isRun() {
    Log.e(TAG, "run")
}
