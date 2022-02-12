package com.example.reminderapp.data.entity

import androidx.room.*
import java.util.*



@Entity(
    tableName = "reminders",
    indices = [
        Index("id", unique = true)
    ]
)

data class Reminder(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val reminderId: Long = 0,
    @ColumnInfo(name = "reminder_message") val reminderMessage: String,
    @ColumnInfo(name = "reminder_time") val reminderTime: String,
    @ColumnInfo(name = "reminder_seen") val reminderSeen: Boolean,
    @ColumnInfo(name = "reminder_date") val reminderDate: String
)



/*
data class Reminder(
    val reminderId: Long,
    val reminderTitle: String,
    val reminderDate: Date?
)
 */






    //Message, location_x, location_y, reminder_time, creation_time, creator_id, reminder_seen
