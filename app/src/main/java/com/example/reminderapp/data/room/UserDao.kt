package com.example.reminderapp.data.room

import androidx.room.*
import com.example.reminderapp.data.entity.User
import kotlinx.coroutines.flow.Flow


@Dao
abstract class UserDao {

    /*
    @Query(value = "SELECT * FROM users WHERE userName = userName")
    abstract suspend fun getUserWithName(name: String): User?
    */


    @Query("SELECT * FROM users WHERE id = :userId")
    abstract fun getUserWithId(userId: Long): User?

    @Query("SELECT * FROM users")
    abstract fun users(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: User): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: User)

    @Delete
    abstract suspend fun delete(entity: User): Int



}