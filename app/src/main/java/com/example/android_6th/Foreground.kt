package com.example.android_6th

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class Foreground : Service() {

    // 알림 채널 ID
    val CHANNEL_ID = "FGS153"
    val NOTI_ID = 153

    // 알림 채널 함수
    fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(CHANNEL_ID, "FOREGROUND", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        createNotificationChannel()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Flo")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .build()

        startForeground(NOTI_ID, notification)

        runBackground()

        return super.onStartCommand(intent, flags, startId)
    }

    fun runBackground(){
        thread(start=true){
            for (i in 0..100){
                Thread.sleep(1000)
                Log.d("서비스", "COUNT===>$i")
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }
}