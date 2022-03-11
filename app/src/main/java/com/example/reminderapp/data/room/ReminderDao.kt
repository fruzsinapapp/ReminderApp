package com.example.reminderapp.data.room

import androidx.room.*
import com.example.reminderapp.data.entity.Reminder
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
abstract class ReminderDao {

    @Query("SELECT  * FROM reminders WHERE reminder_time < :currentTime")
    abstract fun remindersDue(currentTime : Long): Flow<List<Reminder>>




    @Query("SELECT * FROM reminders")
    abstract fun reminders(): Flow<List<Reminder>>


    @Query("SELECT * FROM reminders WHERE id = :reminderId")
    abstract suspend fun reminder(reminderId: Long): Reminder?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: Reminder): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: Reminder)

    @Delete
    abstract suspend fun delete(entity: Reminder): Int

    @Query("UPDATE reminders SET reminder_message = :message, reminder_time = :time WHERE id = :id")
    abstract suspend fun updateTest(message: String,  time: Long,  id: Long)

    @Query("UPDATE reminders SET reminder_seen = :seen WHERE id = :id")
    abstract suspend fun updateSeen(seen: Boolean,  id: Long)





    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entities: Collection<Reminder>)

    @Query("SELECT *FROM reminders WHERE id = :reminderId ")
    abstract suspend fun getReminderWithId(reminderId: Long):Reminder?
}