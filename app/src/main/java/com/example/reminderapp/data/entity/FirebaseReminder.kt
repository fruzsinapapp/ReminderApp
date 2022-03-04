package com.example.reminderapp.data.entity

import androidx.room.ColumnInfo


data class FirebaseReminder(
    var key: String = "",
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var reminderMessage: String ="",
    var reminderTime: Long,
    var reminderSeen: Boolean,
    var creationTime: Long,
    var creatorId: Long = 0,
    var withNotification: Boolean,
    var withLocation: Boolean
)