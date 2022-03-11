package com.example.reminderapp.ui.remindersNear

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

class RemindersNearViewModel(
    private val reminderRepository: ReminderRepository = Graph.reimderRepository
): ViewModel() {
    private val _state = MutableStateFlow(RemindersNearViewState())


    val state: StateFlow<RemindersNearViewState>
        get() = _state




    init {

        viewModelScope.launch {
            reminderRepository.reminders().collect { list ->
                _state.value = RemindersNearViewState(
                    reminders = list
                )
            }


        }
    }


}

data class RemindersNearViewState(
    val reminders: List<Reminder> = emptyList()
)