package com.zy.socketclient

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this@MainActivity, SocketService::class.java))
        startService(Intent(this@MainActivity, ClientService::class.java))

    }
}
