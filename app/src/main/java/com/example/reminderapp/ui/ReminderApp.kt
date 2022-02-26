package com.example.reminderapp.ui

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reminderapp.ReminderAppState
import com.example.reminderapp.rememberReminderAppState
import com.example.reminderapp.ui.allReminders.AllReminders
import com.example.reminderapp.ui.authentication.Authentication
import com.example.reminderapp.ui.edit.Edit
import com.example.reminderapp.ui.firstScreen.FirstScreen
import com.example.reminderapp.ui.home.Home
import com.example.reminderapp.ui.login.Login
import com.example.reminderapp.ui.maps.ReminderLocationMap
import com.example.reminderapp.ui.passcode.Passcode
import com.example.reminderapp.ui.profile.Profile
import com.example.reminderapp.ui.reminder.Reminder


@Composable
fun ReminderApp(
    sharedPreferences: SharedPreferences,
    appState: ReminderAppState = rememberReminderAppState()

){
    NavHost(
        navController = appState.navController,
        startDestination = "authentication"
    ){

        composable(route="authentication"){
            Authentication(navController = appState.navController)
        }
        composable(route="passcode"){
            Passcode(navController = appState.navController,sharedPreferences)
        }
        composable(route="login") {
            Login(navController = appState.navController,sharedPreferences)
        }
        composable(route="home"){
            Home(navController = appState.navController)
        }

        composable(route="profile"){
            Profile(onBackPress = appState::navigateBack)
        }

        composable(route="reminder"){
            Reminder(navController = appState.navController, onBackPress = appState::navigateBack)
        }



        composable(route="firstScreen"){
            FirstScreen(navController = appState.navController)
        }

        composable(route="allReminders"){
            AllReminders(navController = appState.navController, onBackPress = appState::navigateBack)
        }

        composable(route="edit/{reminderId}"){
            entry-> Edit(
            navController = appState.navController,
            (entry.arguments?.getString("reminderId")?:""),
            onBackPress = appState::navigateBack
        )
        }
        composable(route="map/{key}"){
            entry ->
            ReminderLocationMap(
                navController = appState.navController,
                (entry.arguments?.getString("reminderId")?:"")
            )
        }



    }
}