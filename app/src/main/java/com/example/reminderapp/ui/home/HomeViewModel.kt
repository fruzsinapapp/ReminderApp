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
    //private val _selectedReminder = MutableStateFlow<Reminder?>(null)

    val state: StateFlow<HomeViewState>
        get() = _state

/*
    fun onReminderSelected(reminder: Reminder){
        _selectedReminder.value = reminder
    }

 */


    init {

        viewModelScope.launch {
            reminderRepository.reminders().collect { list ->
                _state.value = HomeViewState(
                    reminders = list
                )
            }

            /*
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
                    //selectedReminder = selectedReminder
                )
            }.collect {_state.value = it}

            */
        }
        //loadRemindersFromDb()
    }

/*
    private fun loadRemindersFromDb(){
        val list = mutableListOf(
            Reminder(reminderMessage = "Test1",reminderTime = "23"),
            Reminder(reminderMessage = "Test2",reminderTime = "24"),
            Reminder(reminderMessage = "Test3",reminderTime = "43"),
            Reminder(reminderMessage = "Test4",reminderTime = "55")
        )
        viewModelScope.launch {
            list.forEach{reminder -> reminderRepository.addReminder(reminder)}
        }
    }

 */

}

data class HomeViewState(
    val reminders: List<Reminder> = emptyList()

    //val reminders: List<Reminder> = emptyList(),
    //val selectedReminder: Reminder? = null
)



