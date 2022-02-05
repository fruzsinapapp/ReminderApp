
package com.example.reminderapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "reminders",
    indices = [
        Index("id", unique = true)
    ]
)
data class Reminder(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="id") val reminderId: Long = 0,
    @ColumnInfo(name="message") val message: String,
    //@ColumnInfo(name = "reminder_time") val reminderTime: Long,
    //@ColumnInfo(name = "creation_time") val creationTime: Long,
    //@ColumnInfo(name = "location_x") val locationX: String,
    //@ColumnInfo(name = "location_y") val locationY: String,
    @ColumnInfo(name = "creator_id") val creatorId: String,
    //@ColumnInfo(name = "reminder_seen") val reminderSeen: Boolean,

)




/*
package com.example.reminderapp.data.entity

import java.util.*

data class Reminder(
    val reminderId: Long,
    val reminderTitle: String,
    val reminderDate: Date?
    )
 */
