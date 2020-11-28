package com.example.bngelbooks.logic.network

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.bngelbooks.R
import com.example.bngelbooks.ui.AddOrderLayout.AddOrderActivity

class RecordService : Service() {

    override fun onCreate() {
        super.onCreate()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("record_service","Record",
                NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val record_intent = Intent(this,AddOrderActivity::class.java)
        record_intent.putExtra("from_Service",true)

        val notification = NotificationCompat.Builder(this,"record_service")
            .setContentIntent((PendingIntent
                .getActivity(this,0,record_intent,PendingIntent.FLAG_UPDATE_CURRENT)))
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.rem_icon))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("记账")
            .setContentText("立即打开记账界面")
            .setWhen(System.currentTimeMillis())
            .build()

        startForeground(1,notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
