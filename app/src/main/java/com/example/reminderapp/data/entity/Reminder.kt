package com.example.reminderapp.data.entity

import java.util.*

data class Reminder(
    val reminderId: Long,
    val reminderTitle: String,
    val reminderDate: Date?
    )