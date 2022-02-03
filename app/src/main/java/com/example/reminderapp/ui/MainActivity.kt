package com.example.reminderapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.reminderapp.ui.theme.ReminderAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedpref: SharedPreferences
        val editor: SharedPreferences.Editor

        sharedpref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)

        editor = sharedpref.edit()
        editor.putString("username", "Test1")
        editor.putString("password", "password")
        editor.putString("code", "00000")
        editor.commit()

        super.onCreate(savedInstanceState)
        setContent {
            ReminderAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ReminderApp(sharedpref)
                }
            }
        }
    }
}
