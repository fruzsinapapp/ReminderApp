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
    lati:String,
    longi:String,
    navController: NavController,
    viewModel: SelectedRemindersViewModel = viewModel()

) {
    val x2 = 50
    val y2 = 20
    val dist1 = pow((x2-lati.toDouble()),2.0)
    val dist2 = pow((y2-longi.toDouble()),2.0)

    val results = FloatArray(1)
    Location.distanceBetween(x2.toDouble(), y2.toDouble(),lati.toDouble(),longi.toDouble(),results)
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            SelectedMessages(navController = navController)
            /*
            Text(text = lati)
            Text(text = longi)
            Text(text = results[0].toString())
             */
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
                navController = navController
            )
        }

    }


}