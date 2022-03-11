package com.example.reminderapp.ui.maps

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.reminderapp.R
import com.example.reminderapp.data.entity.Reminder
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

import kotlin.random.Random

class GeofenceReceiver : BroadcastReceiver() {

    lateinit var key: String
    lateinit var text: String

    override fun onReceive(context: Context?, intent: Intent?) {
        println("XYZ")
        if (context != null) {
            val geofencingEvent = GeofencingEvent.fromIntent(intent!!)
            val geofencingTransition = geofencingEvent.geofenceTransition

            if (geofencingTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofencingTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
                // Retrieve data from intent
                if (intent != null) {
                    key = intent.getStringExtra("key")!!
                    text = intent.getStringExtra("message")!!
                }

                //val firebase = Firebase.database
                //val reference = firebase.getReference("reminders")

                //ShowNotification(
                //    context.applicationContext,
                //    "Location"
                //)


                // remove geofence
                //val triggeringGeofences = geofencingEvent.triggeringGeofences
                //MapsActivity.removeGeofences(context, triggeringGeofences)
            }
        }
    }


    fun ShowNotification(context: Context?, message: String) {
        val CHANNEL_ID = "REMINDER_NOTIFICATION_CHANNEL"
        var notificationId = 1589
        notificationId += Random(notificationId).nextInt(1, 30)

        val notificationBuilder = NotificationCompat.Builder(context!!.applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(message)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = context.getString(R.string.app_name)
            }
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}
