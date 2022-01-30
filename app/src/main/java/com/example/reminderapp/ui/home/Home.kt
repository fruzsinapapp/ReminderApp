package com.example.reminderapp.ui.home


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.reminderapp.R
import com.example.reminderapp.ui.home.reminderMessages.ReminderMessages
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Home(
    navController: NavController

) {
    Surface(modifier = Modifier.fillMaxSize()){
        HomeContent(
            navController = navController
        )

    }
}

@Composable
fun HomeContent(
    navController: NavController
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

            HomeAppBar(
                backgroundColor = appBarColor,
                navController = navController)

            ReminderMessages(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
    /*
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Spacer(modifier = Modifier.height(10.dp))


        Button(
            onClick = {navController.navigate("profile")},
            enabled = true,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        ){
            Text(text = "Log out")
        }

    }
    */


}

@Composable
private  fun HomeAppBar(
    backgroundColor: Color,
    navController: NavController
){
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
        },
        backgroundColor = backgroundColor,
        actions = {
            IconButton( onClick = {navController.navigate("login") } ) {
                Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = stringResource(R.string.search))
            }
            IconButton( onClick = {navController.navigate("profile") } ) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = stringResource(R.string.account))
            }
        }
    )
}