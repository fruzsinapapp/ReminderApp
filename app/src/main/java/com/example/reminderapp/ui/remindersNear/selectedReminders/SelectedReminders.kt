package com.example.reminderapp.ui.remindersNear.selectedReminders

import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.ui.maps.LOCATION_REQUEST_CODE
import com.example.reminderapp.ui.remindersNear.selectedReminders.selectedMessages.SelectedMessages
import com.google.accompanist.insets.systemBarsPadding
import java.lang.Math.pow
import java.lang.Math.sqrt
import java.util.*


@Composable
fun SelectedReminders(
    navController: NavController,
    onBackPress: () -> Unit,
    viewModel: SelectedRemindersViewModel = viewModel()

) {

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            SelectedMessages(navController = navController, onBackPress = onBackPress )

        }
    }

/*
    val viewState by viewModel .state.collectAsState()


    if(viewState.reminders.isNotEmpty()){
        Surface(modifier = Modifier.fillMaxSize()){
            SelectedRemindersContent(
                reminders=viewState.reminders,
                navController = navController
            )
        }
    }
*/
}

@Composable
fun SelectedRemindersContent(
    reminders: List<Reminder>,
    onBackPress: () -> Unit,
    navController: NavController

){
    Scaffold (
        modifier = Modifier.padding(bottom = 24.dp),

    ){
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
        ){
            val appBarColor = MaterialTheme.colors.secondary.copy(alpha = 0.80f)
            val calendar = Calendar.getInstance()
            val currentTime = calendar.timeInMillis

            SelectedMessages(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                onBackPress = onBackPress
            )
        }

    }


}