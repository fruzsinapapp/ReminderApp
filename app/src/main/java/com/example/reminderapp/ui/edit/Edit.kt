package com.example.reminderapp.ui.edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reminderapp.R
import com.google.accompanist.insets.systemBarsPadding


@Composable
fun Edit(
    navController: NavController,
    reminderId: String,
    onBackPress: () -> Unit

) {

        val selectedReminder=reminderId.toLong()
        Column() {
            println(reminderId)
            Text(selectedReminder.toString())

        }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            TopAppBar {
                IconButton(
                    onClick = onBackPress
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(text = "Main page")
            }


            println(reminderId)
            Text(selectedReminder.toString())
            Text(
                text = "Edit selected reminder",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(16.dp),
                color = Color.Black
            )


            Image(
                modifier = Modifier,
                painter = painterResource(R.drawable.background1),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )




        }
    }


}


