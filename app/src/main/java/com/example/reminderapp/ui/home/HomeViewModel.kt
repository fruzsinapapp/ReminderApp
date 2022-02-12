package com.example.reminderapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.Graph
import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.data.repository.ReminderRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val reminderRepository: ReminderRepository = Graph.reimderRepository
): ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())


    val state: StateFlow<HomeViewState>
        get() = _state




    init {

        viewModelScope.launch {
            reminderRepository.reminders().collect { list ->
                _state.value = HomeViewState(
                    reminders = list
                )
            }


        }
        loadRemindersFromDb()
    }


    private fun loadRemindersFromDb(){
        val list = mutableListOf(
            Reminder(reminderMessage = "Test1",reminderTime = "23",reminderSeen = false,reminderDate = "23"),
            Reminder(reminderMessage = "Test2",reminderTime = "24",reminderSeen = false,reminderDate = "23"),
            Reminder(reminderMessage = "Test3",reminderTime = "43",reminderSeen = false,reminderDate = "23"),
            Reminder(reminderMessage = "Test4",reminderTime = "55",reminderSeen = false,reminderDate = "23")
        )
        viewModelScope.launch {
            list.forEach{reminder -> reminderRepository.addReminder(reminder)}
        }
    }



}

data class HomeViewState(
    val reminders: List<Reminder> = emptyList()
)



