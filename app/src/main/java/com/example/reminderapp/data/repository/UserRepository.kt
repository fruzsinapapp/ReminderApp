package com.example.reminderapp.data.repository

import com.example.reminderapp.data.entity.User
import com.example.reminderapp.data.room.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository (
    private val userDao: UserDao
) {
    fun users(): Flow<List<User>> = userDao.users()
    fun getUserWithId(userId: Long): User? = userDao.getUserWithId(userId)
}