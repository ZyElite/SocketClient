package com.zy.socketclient

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import io.realm.Realm
import io.realm.RealmConfiguration

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }

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