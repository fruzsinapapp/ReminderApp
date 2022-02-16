package com.example.reminderapp.ui.home.reminderMessages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.Graph
import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.data.repository.ReminderRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.*



class ReminderMessagesViewModel(
    //private val selectedReminder: Reminder,
    //private val currentTime: Long,




    private val reminderRepository: ReminderRepository = Graph.reimderRepository,




): ViewModel(){
    private val _state = MutableStateFlow(ReminderMessagesViewState())
    private val calendar=Calendar.getInstance()
    private val currentTime = calendar.timeInMillis

    val state: StateFlow<ReminderMessagesViewState>
        get() = _state

    init {
        viewModelScope.launch {

            reminderRepository.remindersDue(currentTime).collect { list ->
                _state.value = ReminderMessagesViewState(
                    reminders = list
                )
            }

            /*
            reminderRepository.reminders().collect { list ->
                _state.value = ReminderMessagesViewState(
                    reminders = list
                )
            }

             */
        }

    }

    suspend fun deleteReminder(reminder: Reminder): Int {
        return reminderRepository.deleteReminder(reminder)
    }

    suspend fun updateSeen(seen: Boolean,id: Long) {
        return reminderRepository.updateSeen(seen, id)
    }

}

data class ReminderMessagesViewState(
    val reminders: List<Reminder> = emptyList()
)


