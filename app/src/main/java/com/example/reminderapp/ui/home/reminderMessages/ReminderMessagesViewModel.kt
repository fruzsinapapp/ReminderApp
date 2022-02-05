package com.example.reminderapp.ui.home.reminderMessages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.Graph.reminderRepository
import com.example.reminderapp.data.entity.Reminder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class ReminderMessagesViewModel: ViewModel(){
    private val _state = MutableStateFlow(ReminderMessagesViewState())

    val state: StateFlow<ReminderMessagesViewState>
        get() = _state


    init {
        viewModelScope.launch {
            reminderRepository.reminders()
        }
    }
    /*
    init {
        val list = mutableListOf<Reminder>()
        for (x in 1..20) {
            list.add(
                Reminder(
                    reminderId = x.toLong(),
                    reminderTitle = "$x reminder",
                    reminderDate = Date()
                )
            )
        }

        viewModelScope.launch {
            _state.value = ReminderMessagesViewState(
                reminders = list
            )
        }
    }
    */
}


data class ReminderMessagesViewState(
    val reminders: List<Reminder> = emptyList()
)

