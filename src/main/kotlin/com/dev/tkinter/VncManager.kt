package com.dev.tkinter

import android.util.Log
import kotlinx.coroutines.*
import java.net.Socket

/**
 * VncManager - APK Version
 * 
 * Handles VNC connections to remote servers running Python Tkinter applications.
 * For the APK, this doesn't manage servers - just connects to existing VNC servers.
 */
class VncManager {
    private var vncSocket: Socket? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    
    companion object {
        private const val TAG = "VncManager"
        const val DEFAULT_VNC_PORT = 5900
    }
    
    /**
     * Connect to a remote VNC server
     */
    fun connect(host: String, port: Int = DEFAULT_VNC_PORT) {
        scope.launch {
            try {
                Log.d(TAG, "Connecting to VNC server at $host:$port")
                vncSocket = Socket(host, port)
                Log.d(TAG, "Successfully connected to VNC server")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to connect to VNC server", e)
                throw e
            }
        }
    }
    
    /**
     * Disconnect from VNC server
     */
    fun disconnect() {
        try {
            vncSocket?.close()
            vncSocket = null
            Log.d(TAG, "Disconnected from VNC server")
        } catch (e: Exception) {
            Log.e(TAG, "Error disconnecting", e)
        }
    }
    
    /**
     * Check if connected to VNC server
     */
    fun isConnected(): Boolean = vncSocket?.isConnected ?: false
    
    /**
     * Send data to VNC server
     */
    fun sendData(data: ByteArray) {
        try {
            vncSocket?.getOutputStream()?.write(data)
            vncSocket?.getOutputStream()?.flush()
        } catch (e: Exception) {
            Log.e(TAG, "Error sending data to VNC server", e)
        }
    }
    
    /**
     * Receive data from VNC server
     */
    fun receiveData(): ByteArray? {
        return try {
            val inputStream = vncSocket?.getInputStream() ?: return null
            val buffer = ByteArray(8192)
            val bytesRead = inputStream.read(buffer)
            if (bytesRead > 0) {
                buffer.copyOfRange(0, bytesRead)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error receiving data from VNC server", e)
            null
        }
    }
    
    /**
     * Cleanup resources
     */
    fun cleanup() {
        disconnect()
        scope.cancel()
    }
}
