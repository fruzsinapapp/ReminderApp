package com.example.reminderapp.ui.reminder


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.from

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import com.example.reminderapp.Graph
import com.example.reminderapp.R
import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.data.repository.ReminderRepository
import com.example.reminderapp.ui.MainActivity
import com.example.reminderapp.util.NotificationWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class ReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reimderRepository,




): ViewModel() {
    private val _state = MutableStateFlow(ReminderViewState())
    val context = Graph.appContext
    val state: StateFlow<ReminderViewState>
        get() = _state

    suspend fun saveReminder(reminder: Reminder): Long {
        setDelayedNotification(reminder,context)
        setNotificationBefore(reminder,context)
        return reminderRepository.addReminder(reminder)
    }

    init {
        createNotificationChannel(context = Graph.appContext)
        //setOneTimeNotification()
        viewModelScope.launch {
            reminderRepository.reminders().collect { reminders ->
                _state.value = ReminderViewState(reminders)
            }
        }
    }
}

private fun setDelayedNotification(reminder : Reminder,context: Context){
    val workManager = WorkManager.getInstance(Graph.appContext)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val timeNow = Calendar.getInstance()
    val reminderTime = reminder.reminderTime
    val delay = reminderTime-timeNow.timeInMillis

    val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(delay,TimeUnit.MILLISECONDS)
        .setConstraints(constraints)
        .build()

    workManager.enqueue(notificationWorker)

    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever{workinfo ->
            if (workinfo.state == WorkInfo.State.SUCCEEDED){
                //createSuccessNotification()
                createReminderNotification(reminder,context)
            }else{
                createFailedNotification()
            }
        }
}


//notification 2 minutes before
private fun setNotificationBefore(reminder : Reminder,context: Context){
    val workManager = WorkManager.getInstance(Graph.appContext)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val timeNow = Calendar.getInstance()
    val reminderTime = reminder.reminderTime
    val delay = reminderTime-timeNow.timeInMillis-120000

    val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(delay,TimeUnit.MILLISECONDS)
        .setConstraints(constraints)
        .build()

    workManager.enqueue(notificationWorker)

    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever{workinfo ->
            if (workinfo.state == WorkInfo.State.SUCCEEDED){
                //createSuccessNotification()
                createReminderNotification(reminder,context)
            }else{
                //createErrorNotification()
            }
        }
}

/*
private fun setOneTimeNotification(){
    val workManager = WorkManager.getInstance(Graph.appContext)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)

        .build()

    val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(10,TimeUnit.SECONDS)
        .setConstraints(constraints)
        .build()

    workManager.enqueue(notificationWorker)

    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever{workinfo ->
            if (workinfo.state == WorkInfo.State.SUCCEEDED){
                //createSuccessNotification()
            }else{
                //createErrorNotification()
            }
        }
}
*/

private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "RemindernotificationChannel"
        val descriptionText = "ReminderNotificationChannelDescriptionText"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

/*
private fun createSuccessNotification(){
    val notificationId = 1
    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("Success!")
        .setContentText("Countdown completed successfully.")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(Graph.appContext)){
        notify(notificationId,builder.build())
    }
}
 */


private fun createFailedNotification(){
    val notificationId = 1
    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("Fail!")
        .setContentText("Notification failed")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(Graph.appContext)){
        notify(notificationId,builder.build())
    }
}


private fun createReminderNotification(
    reminder:Reminder,
    context: Context
){
    val notificationId = 2

    val intent = Intent(context, MainActivity::class.java).apply{
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0,intent,0)

    val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("You have one reminder due")
        .setContentText("Reminder message:${reminder.reminderMessage}")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .setSound(uri)
        .setOnlyAlertOnce(true)

    with(NotificationManagerCompat.from(Graph.appContext)){
        notify(notificationId,builder.build())

    }
}

/*
private fun createReminderNotification(reminder: Reminder){
    val notificationId = 2
    val builder = NotificationCompat.Builder(Graph.appContext,"CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("Reminder created")
        .setContentText("You created a reminder")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    with(NotificationManagerCompat.from(Graph.appContext)){
        notify(notificationId, builder.build())
    }
}
 */


data class ReminderViewState(
    val reminders: List<Reminder> = emptyList()
)