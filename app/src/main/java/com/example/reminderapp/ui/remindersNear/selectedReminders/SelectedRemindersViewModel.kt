package com.example.reminderapp.ui.remindersNear.selectedReminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.Graph
import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.data.repository.ReminderRepository
import com.example.reminderapp.ui.home.HomeViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*


class SelectedRemindersViewModel(
    private val reminderRepository: ReminderRepository = Graph.reimderRepository
): ViewModel() {
    private val _state = MutableStateFlow(SelectedRemindersViewState())


    val state: StateFlow<SelectedRemindersViewState>
        get() = _state


    init {

        viewModelScope.launch {
            reminderRepository.reminders().collect { list ->
                _state.value = SelectedRemindersViewState(
                    reminders = list
                )
            }
        }
    }
}




data class SelectedRemindersViewState(
    val reminders: List<Reminder> = emptyList()
)

