package com.dev.tkinter

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * VNC Service - Handles VNC connections in background
 */
class VncService : Service() {
    private val binder = LocalBinder()
    private val scope = CoroutineScope(Dispatchers.Default)
    private lateinit var vncManager: VncManager

    inner class LocalBinder : Binder() {
        fun getService(): VncService = this@VncService
    }

    override fun onCreate() {
        super.onCreate()
        vncManager = VncManager()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder = binder

    override fun onDestroy() {
        super.onDestroy()
        scope.coroutineContext.cancel()
    }

    fun connectToVnc(host: String, port: Int) {
        scope.launch {
            try {
                vncManager.connect(host, port)
            } catch (e: Exception) {
                android.util.Log.e("VncService", "VNC Connection Error", e)
            }
        }
    }

    fun disconnectVnc() {
        vncManager.disconnect()
    }
}
