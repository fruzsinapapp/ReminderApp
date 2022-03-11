package com.example.reminderapp.ui.remindersNear.selectedReminders.selectedMessages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.Graph
import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.data.repository.ReminderRepository
import com.example.reminderapp.ui.home.reminderMessages.ReminderMessagesViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*


class SelectedMessagesViewModel(

    private val reminderRepository: ReminderRepository = Graph.reimderRepository,

    ): ViewModel(){
    private val _state = MutableStateFlow(SelectedMessagesViewState())
    private val calendar= Calendar.getInstance()
    private val currentTime = calendar.timeInMillis

    val state: StateFlow<SelectedMessagesViewState>
        get() = _state

    init {
        viewModelScope.launch {

            reminderRepository.remindersDue(currentTime).collect { list ->
                _state.value = SelectedMessagesViewState(
                    reminders = list
                )
            }

        }

    }

}

data class SelectedMessagesViewState(
    val reminders: List<Reminder> = emptyList()
)