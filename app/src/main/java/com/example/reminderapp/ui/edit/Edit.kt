package com.example.reminderapp.ui.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.codemave.mobilecomputing.util.viewModelProviderFactoryOf
import com.example.reminderapp.ui.home.HomeViewModel
import org.w3c.dom.Text


@Composable
fun Edit(
    reminderId: Long,
    modifier: Modifier

) {
    val viewModel: EditViewModel = viewModel(
        key = "selected_reminder",
        factory = viewModelProviderFactoryOf { EditViewModel(reminderId) }
    )
    val viewState by viewModel.state.collectAsState()
    val selectedReminder = viewState.reminder

    Column(){
        Text(text = "HELLO")
        //Text(selectedReminder.toString())
    }


}