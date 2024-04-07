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
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class Foreground : Service() {

    // foreground가 띄우는 알림 채널 ID
    val CHANNEL_ID = "Flo"
    val NOTI_ID = 121

    // 메인 쓰레드에서 실행될 사용자 정의 Scope
    val scope = CoroutineScope(Dispatchers.Main)


    // 알림 채널 만드는 함수
    fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(CHANNEL_ID, "FOREGROUND", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel) //채널 선언

        }
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()

        // 카운트를 증가시키고 progress bar를 업데이트하는 함수를 호출
        startCounting()

        return super.onStartCommand(intent, flags, startId)
    }

    // 카운트를 증가시키고 progress bar를 업데이트하는 함수
    private fun startCounting(){
        scope.launch {
            var count = 0
            while (isActive) {
                count++
                // 카운트 값을 업데이트하고 progress bar 업데이트하는 함수 호출
                updateNotification(count)
                delay(1000) // 1초씩 카운트 증가
            }
        }
    }

    // 알림을 업데이트하는 함수
    @SuppressLint("ForegroundServiceType")
    private fun updateNotification(progress: Int) {

        //Notification 클릭했을 때 실행되는 액티비티 설정
        val notificationIntent = Intent(this, SplashActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        //Notification 설정
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Flo") //제목
            .setContentText("오늘의 음악을 감상해보세요! Count: $progress") //내용
            .setSmallIcon(R.drawable.ic_my_like_on) //아이콘
            .setContentIntent(pendingIntent) // PendingIntent 설정
            .setProgress(1000, progress, false) // progress bar 설정
            .build()

        startForeground(NOTI_ID, notification)
    }

    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }
}
