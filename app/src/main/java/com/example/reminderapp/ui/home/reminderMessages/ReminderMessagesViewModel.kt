package com.example.reminderapp.ui.home.reminderMessages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.Graph
import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.data.repository.ReminderRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*



class ReminderMessagesViewModel(
    private val reminderRepository: ReminderRepository = Graph.reimderRepository,

    //private val reminderId: Long

): ViewModel(){
    private val _state = MutableStateFlow(ReminderMessagesViewState())

    /*
    private val _selectedReminder= MutableStateFlow<Reminder?>(null)
    fun onReminderSelected(reminder: Reminder){
        _selectedReminder.value = reminder
    }

     */



    val state: StateFlow<ReminderMessagesViewState>
        get() = _state

    init {
        viewModelScope.launch {
            reminderRepository.reminders().collect { list ->
                _state.value = ReminderMessagesViewState(
                    reminders = list
                )
            }
            /*
            combine(
                reminderRepository.reminders().onEach { list ->
                    _state.value = ReminderMessagesViewState(
                        reminders = list
                    )
                },
                _selectedReminder
            ){reminders, selectedReminder ->
                ReminderMessagesViewState(
                    reminders = reminders,
                    selectedReminder = selectedReminder
                )

            }.collect{_state.value = it}
            */


        }

    }

     suspend fun deleteReminder(reminder: Reminder): Int {
        return reminderRepository.deleteReminder(reminder)
    }

   /*
    suspend fun updateReminder(reminder: Reminder) {
        return reminderRepository.updateReminder(reminder)
    }

    */
}


data class ReminderMessagesViewState(
    val reminders: List<Reminder> = emptyList(),


    //val selectedReminder: Reminder? = null
)


