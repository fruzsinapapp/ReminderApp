package com.example.reminderapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reminderapp.data.entity.Reminder

@Database(
    entities = [Reminder::class],
    version = 11,
    exportSchema = false
)

abstract class ReminderAppDatabase : RoomDatabase() {
    abstract fun reminderDao():ReminderDao
}