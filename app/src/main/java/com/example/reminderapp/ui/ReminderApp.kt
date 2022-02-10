package com.example.reminderapp.ui

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reminderapp.ReminderAppState
import com.example.reminderapp.rememberReminderAppState
import com.example.reminderapp.ui.authentication.Authentication
import com.example.reminderapp.ui.edit.Edit
import com.example.reminderapp.ui.home.Home
import com.example.reminderapp.ui.login.Login
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
            Reminder(onBackPress = appState::navigateBack)
        }


        composable(route="edit/{reminderId}"){
            entry-> Edit(
            navController = appState.navController,
            (entry.arguments?.getString("reminderId")?:""),
            onBackPress = appState::navigateBack
        )


        }




    }
}