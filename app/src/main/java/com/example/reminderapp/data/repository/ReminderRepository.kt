package com.example.reminderapp.data.repository

import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.data.room.ReminderDao
import kotlinx.coroutines.flow.Flow

class ReminderRepository(private val reminderDao: ReminderDao){
    suspend fun addReminder(reminder: Reminder) = reminderDao.insert(reminder)
    fun reminders(): Flow<List<Reminder>> = reminderDao.categories()
}