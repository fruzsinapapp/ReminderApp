package com.example.reminderapp.ui.edit

import androidx.compose.runtime.MutableState
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

class EditViewModel(
    private val reminderId: Long,
    private val reminderRepository: ReminderRepository = Graph.reimderRepository

): ViewModel(){
    private val _state = MutableStateFlow(EditViewState())

    val state: StateFlow<EditViewState>
        get() = _state

    init {
        viewModelScope.launch {
                _state.value = EditViewState(
                    reminder = reminderRepository.getReminderWithId(reminderId)
                )
        }

    }

    suspend fun deleteReminder(reminder: Reminder): Int {
        return reminderRepository.deleteReminder(reminder)
    }

    suspend fun updateReminder(reminder: Reminder) {
        return reminderRepository.updateReminder(reminder)
    }
}


data class EditViewState(
    val reminder: Reminder? = null
)