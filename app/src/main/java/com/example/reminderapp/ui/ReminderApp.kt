package com.example.reminderapp.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reminderapp.ReminderAppState
import com.example.reminderapp.rememberReminderAppState
import com.example.reminderapp.ui.home.Home
import com.example.reminderapp.ui.login.Login
import com.example.reminderapp.ui.profile.Profile
import com.example.reminderapp.ui.reminder.Reminder


@Composable
fun ReminderApp(
    sharedPreferences: SharedPreferences,
    appState: ReminderAppState = rememberReminderAppState()

){
    NavHost(

        navController = appState.navController,
        startDestination = "login"
    ){
        composable(route="login") {

            Login(navController = appState.navController,sharedPreferences) //to log in we need a button, and this button will use a navcontroller
            //Login()
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
    }
}