package com.example

import android.content.Intent
import androidx.core.app.JobIntentService
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.app.NotificationManager
import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.app.PendingIntent
import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.NotificationCompat
import com.example.myapplication.activity.DietPlanActivity
import android.app.job.JobService
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.media.RingtoneManager
import android.os.Build
import com.example.myapplication.AssigmentApplication
import com.example.myapplication.AssigmentApplication.Companion.getApplicationInstance


class DietIntentService : JobIntentService() {
    internal var CHANNEL_ID = "my_channel_01"// The id of the channel.

    companion object {
        fun enqueueWork(context: Context, value: String) {
            val intent = Intent(context, DietIntentService::class.java)
            intent.putExtra("meal_name", value)
            enqueueWork(context, DietIntentService::class.java, 0, intent)
        }
    }

    override fun onHandleWork(intent: Intent) {
         sendNotification(intent)

    }

    private fun sendNotification(intent1: Intent) {
        val intent =
            Intent(AssigmentApplication.getApplicationInstance(), DietPlanActivity::class.java)
        intent.setAction("NOTIFICATION")
        intent.putExtra("NOTIFICATION", true)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(
            AssigmentApplication.getApplicationInstance(), 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val name = "Diet chart"// The user-visible name of the channel.
        val importance = NotificationManager.IMPORTANCE_HIGH

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.alert_dark_frame)
            .setContentTitle("Take Your food \n"+intent1.getStringExtra("meal_name"))
            .setAutoCancel(true)
            .setChannelId(CHANNEL_ID)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager =
            getApplicationInstance().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationManager.createNotificationChannel(mChannel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

}