package com.example.reminderapp.data.repository

import androidx.room.Query
import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.data.room.ReminderDao
import kotlinx.coroutines.flow.Flow

class ReminderRepository(
    private val reminderDao: ReminderDao
    ){

    suspend fun addReminder(reminder: Reminder):Long{
        return when (val local = reminderDao.reminder(reminder.reminderId)){
            null -> reminderDao.insert(reminder)
            else -> local.reminderId

        }
    }

    suspend fun updateTest(message: String, time: Long, id: Long ){
        return reminderDao.updateTest(message,time, id)
    }

    suspend fun updateSeen(seen: Boolean, id:Long){
        return reminderDao.updateSeen(seen, id)
    }

    suspend fun deleteReminder(reminder: Reminder):Int{
        return reminderDao.delete(reminder)
    }

    suspend fun updateReminder(reminder: Reminder){
        return reminderDao.update(reminder)
    }

    //suspend fun addReminder(reminder: Reminder) = reminderDao.insert(reminder)
    fun reminders(): Flow<List<Reminder>> = reminderDao.reminders()

    fun remindersDue(currentTime : Long): Flow<List<Reminder>> = reminderDao.remindersDue(currentTime)

    //fun remindersNear(lati: Double, longi: Double): Flow<List<Reminder>> = reminderDao.remindersNear(lati,longi)


    suspend fun getReminderWithId(reminderId: Long): Reminder? = reminderDao.getReminderWithId(reminderId)

}