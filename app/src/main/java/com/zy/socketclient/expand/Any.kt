package com.zy.socketclient.expand

import io.realm.Realm
import io.realm.RealmResults

val mRealm = Realm.getDefaultInstance()!!


/**
 * Synchronous access
 */
fun save(body: (realm: Realm) -> Unit) {
    save({ mRealm.beginTransaction() }, {
        body(mRealm)
    }, { mRealm.commitTransaction() })
}

fun save() {

}
/**
 * query
 */
fun query(function: (realm: Realm) -> Unit) {
    function(mRealm)
}

/**
 * return Result set
 */

fun <T> query(function: (realm: Realm) -> RealmResults<T>): RealmResults<T> {
    return function(mRealm)
}

private fun save(start: () -> Unit, body: (realm: Realm) -> Unit, end: () -> Unit) {
    start()
    body(mRealm)
    end()
}