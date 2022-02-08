package com.example.reminderapp.data.repository

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

    suspend fun deleteReminder(reminder: Reminder):Int{
        return reminderDao.delete(reminder)
    }

    suspend fun updateReminder(reminder: Reminder){
        return reminderDao.update(reminder)
    }

    //suspend fun addReminder(reminder: Reminder) = reminderDao.insert(reminder)
    fun reminders(): Flow<List<Reminder>> = reminderDao.reminders()
}