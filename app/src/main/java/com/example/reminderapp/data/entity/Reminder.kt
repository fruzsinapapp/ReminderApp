package com.example.reminderapp.data.entity

import androidx.room.*


@Entity(
    tableName = "reminders",
    indices = [
        Index("id", unique = true)
    ]
)

data class Reminder(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val reminderId: Long = 0,
    @ColumnInfo(name = "reminder_message") val reminderMessage: String,
    @ColumnInfo(name = "reminder_time") val reminderTime: Long,
    @ColumnInfo(name = "reminder_seen") val reminderSeen: Boolean,
    @ColumnInfo(name = "creation_time") val creationTime: Long,
    @ColumnInfo(name = "Location_x") val locationX: Double?,
    @ColumnInfo(name = "location_y") val locationY: Double?,
    @ColumnInfo(name = "creator_id") val creatorId: Long = 0,

    @ColumnInfo(name = "with_notification") val withNotification: Boolean
)

