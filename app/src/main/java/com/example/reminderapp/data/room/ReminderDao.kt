package com.example.reminderapp.data.room

import androidx.room.*
import com.example.reminderapp.data.entity.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ReminderDao {

    @Query("""SELECT * FROM reminders WHERE id = :reminderId""")
    abstract fun reminder(reminderId: Long): Reminder?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: Reminder): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: Reminder)

    @Delete
    abstract suspend fun delete(entity: Reminder): Int




    @Query("SELECT * FROM reminders LIMIT 15")
    abstract fun categories(): Flow<List<Reminder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entities: Collection<Reminder>)

}