package com.example.reminderapp.ui.home.reminderMessages

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



class ReminderMessagesViewModel(
    private val reminderRepository: ReminderRepository = Graph.reimderRepository
): ViewModel(){
    private val _state = MutableStateFlow(ReminderMessagesViewState())

    val state: StateFlow<ReminderMessagesViewState>
        get() = _state

    init {
        viewModelScope.launch {
            reminderRepository.reminders().collect { list ->
                _state.value = ReminderMessagesViewState(
                    reminders = list
                )
            }
        }

    }

     suspend fun deleteReminder(reminder: Reminder): Int {
        return reminderRepository.deleteReminder(reminder)
    }
}


data class ReminderMessagesViewState(
    val reminders: List<Reminder> = emptyList()
)


