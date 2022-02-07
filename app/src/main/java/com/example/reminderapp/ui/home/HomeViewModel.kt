package com.example.reminderapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.Graph
import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val reminderRepository: ReminderRepository = Graph.reimderRepository
): ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())
    private val _selectedReminder = MutableStateFlow<Reminder?>(null)

    val state: StateFlow<HomeViewState>
        get() = _state


    fun onReminderSelected(reminder: Reminder){
        _selectedReminder.value = reminder
    }



    init {
        viewModelScope.launch {
            combine(
                reminderRepository.reminders().onEach { list ->
                    if(list.isNotEmpty() && _selectedReminder.value == null){
                        _selectedReminder.value = list[0]
                    }
                },
                _selectedReminder

            ) { reminders, selectedReminder ->
                HomeViewState(
                    reminders = reminders,
                    selectedReminder = selectedReminder
                )
            }.collect {_state.value = it}
        }
        loadRemindersFromDb()
    }

    private fun loadRemindersFromDb(){
        val list = mutableListOf(
            Reminder(reminderMessage = "Test1",reminderTime = 20),
            Reminder(reminderMessage = "Test2",reminderTime = 30),
            Reminder(reminderMessage = "Test3",reminderTime = 40),
            Reminder(reminderMessage = "Test4",reminderTime = 50)
        )
        viewModelScope.launch {
            list.forEach{reminder -> reminderRepository.addReminder(reminder)}
        }
    }
}

data class HomeViewState(
    val reminders: List<Reminder> = emptyList(),
    val selectedReminder: Reminder? = null
)



