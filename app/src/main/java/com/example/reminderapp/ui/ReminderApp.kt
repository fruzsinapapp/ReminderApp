package com.example.reminderapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reminderapp.ReminderAppState
import com.example.reminderapp.rememberReminderAppState


@Composable
fun ReminderApp(
    appState: ReminderAppState = rememberReminderAppState()
){
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ){
        composable(route="login") {
            Login(navController = appState.navController) //to log in we need a button, and this button will use a navcontroller
        }
    }
}