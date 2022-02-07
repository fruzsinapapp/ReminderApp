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
    /*
    init {
        val list = mutableListOf<Reminder>()
        for (x in 1..20) {
            list.add(
                Reminder(
                    reminderId = x.toLong(),

                    reminderMessage = "$x reminder",
                    reminderTime = "123"
                    /*
                    reminderTitle = "$x reminder",
                    reminderDate = Date()
                     */
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


