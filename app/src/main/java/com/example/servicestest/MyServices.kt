package com.example.servicestest

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyServices: Service() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        val start = intent?.getIntExtra(EXTRA_START, 0) ?: 0
        coroutineScope.launch {
            for (i in start until start + 100) {
                delay(1000)
                log("Timer $i")
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyService: $message")
    }

    companion object {

        private const val EXTRA_START = "start"
        fun newIntent(context: Context, start: Int): Intent {
            return Intent(context, MyServices::class.java).apply {
                putExtra(EXTRA_START, start)
            }
        }
    }

}