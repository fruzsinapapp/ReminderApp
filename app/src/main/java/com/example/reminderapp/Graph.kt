package com.example.reminderapp

import android.content.Context
import androidx.room.Room
import com.example.reminderapp.data.repository.ReminderRepository
import com.example.reminderapp.data.room.ReminderAppDatabase

object Graph {
    lateinit var database:ReminderAppDatabase

    val reminderRepository by lazy{
        ReminderRepository(
            reminderDao = database.reminderDao()
        )
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, ReminderAppDatabase::class.java, "remData.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}