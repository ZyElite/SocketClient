package com.zy.socketclient

import android.app.Application
import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //初始化Realm数据库
        Realm.init(this)
        //配置Realm数据库
        val config = RealmConfiguration.Builder()
                .schemaVersion(1)
                .name(resources.getString(R.string.app_name)).build()
        Realm.setDefaultConfiguration(config)
    }
}