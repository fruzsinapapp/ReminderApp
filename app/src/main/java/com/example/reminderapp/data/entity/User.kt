package com.example.reminderapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index("userName", unique = true),
        Index("password", unique = true)
    ]
)

data class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name ="id") val id: Long = 0,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "password") val password: String
)