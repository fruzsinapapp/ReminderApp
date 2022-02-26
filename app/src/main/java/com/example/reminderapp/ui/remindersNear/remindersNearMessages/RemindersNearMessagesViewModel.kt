package com.example.reminderapp.ui.remindersNear.remindersNearMessages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.Graph
import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*


class RemindersNearMessagesViewModel(

    private val reminderRepository: ReminderRepository = Graph.reimderRepository,

    ): ViewModel(){
    private val _state = MutableStateFlow(RemindersNearMessagesViewState())
    private val calendar= Calendar.getInstance()
    private val currentTime = calendar.timeInMillis

    val state: StateFlow<RemindersNearMessagesViewState>
        get() = _state

    init {
        viewModelScope.launch {

            reminderRepository.remindersDue(currentTime).collect { list ->
                _state.value = RemindersNearMessagesViewState(
                    reminders = list
                )
            }
        }

    }

    suspend fun deleteReminder(reminder: Reminder): Int {
        return reminderRepository.deleteReminder(reminder)
    }

    suspend fun updateSeen(seen: Boolean,id: Long) {
        return reminderRepository.updateSeen(seen, id)
    }

}

data class RemindersNearMessagesViewState(
    val reminders: List<Reminder> = emptyList()
)


