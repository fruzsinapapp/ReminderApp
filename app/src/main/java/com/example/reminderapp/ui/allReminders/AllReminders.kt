package com.example.reminderapp.ui.allReminders

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.ui.home.HomeContent
import com.example.reminderapp.ui.home.HomeViewModel
import com.google.accompanist.insets.systemBarsPadding
import java.util.*

@Composable
fun AllReminders(
    viewModel: AllRemindersViewModel = viewModel(),
    navController: NavController,
    onBackPress: () -> Unit
) {

    val viewState by viewModel .state.collectAsState()


    if(viewState.reminders.isNotEmpty()){
        Surface(modifier = Modifier.fillMaxSize()){
            AllRemindersContent(
                reminders=viewState.reminders,
                navController = navController,
                onBackPress = onBackPress
            )
        }
    }

}

@Composable
fun AllRemindersContent(
    reminders: List<Reminder>,
    navController: NavController,
    onBackPress: () -> Unit

){
    Scaffold (
        modifier = Modifier.padding(bottom = 24.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = "reminder") },
                contentColor = Color.Black,
                modifier = Modifier.padding(all=20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ){
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
        ){
            val appBarColor = MaterialTheme.colors.secondary.copy(alpha = 0.80f)
            val calendar = Calendar.getInstance()
            val currentTime = calendar.timeInMillis

            AllRemindersAppBar(
                backgroundColor = appBarColor,
                navController = navController,
                onBackPress = onBackPress)

            AllReminderMessages(
                //currentTime = currentTime,
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )
        }

    }


}

@Composable
fun AllRemindersAppBar (   backgroundColor: Color,
navController: NavController,
                           onBackPress: () -> Unit
){
    TopAppBar(
        title = {
            Text(
                text = stringResource(com.example.reminderapp.R.string.app_name),
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
        },
        backgroundColor = backgroundColor,
        actions = {
            IconButton( onClick = {navController.navigate("authentication") } ) {
                Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = stringResource(com.example.reminderapp.R.string.exit))
            }
            IconButton( onClick = {navController.navigate("profile") } ) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = stringResource(
                    com.example.reminderapp.R.string.account)
                )
            }
        }
    )
}

