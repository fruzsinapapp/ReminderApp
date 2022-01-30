package com.example.reminderapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reminderapp.data.entity.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract  class ReminderAppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
}