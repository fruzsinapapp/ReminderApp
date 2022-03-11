package com.example.reminderapp.ui.firstScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun FirstScreen(
    navController: NavController
){
    Surface(modifier = Modifier.fillMaxSize(), color = Color.LightGray, ) {
/*
        Image(
            modifier = Modifier,
            painter = painterResource(R.drawable.background1),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
 */

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    navController.navigate("home")
                } ,
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ){
                Text(text = "Home")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    navController.navigate("allReminders")
                } ,
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ){
                Text(text = "All the reminders")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    navController.navigate("remindersNear")
                } ,
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ){
                Text(text = "Reminders near you")
            }
        }
    }
}