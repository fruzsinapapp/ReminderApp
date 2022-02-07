package com.example.reminderapp.ui.home


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.reminderapp.R
import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.ui.home.reminderMessages.ReminderMessages
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Home(
    viewModel: HomeViewModel = viewModel(),
    navController: NavController

) {

    val viewState by viewModel .state.collectAsState()

    //val selectedReminder = viewState.selectedReminder

    if(viewState.reminders.isNotEmpty()){
        Surface(modifier = Modifier.fillMaxSize()){
            HomeContent(
                reminders=viewState.reminders,
                navController = navController
            )
        }
    }

}

@Composable
fun HomeContent(
    reminders: List<Reminder>,
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

            /*
            ReminderList(
                reminders = reminders
            )
             */

            ReminderMessages(
                modifier = Modifier.fillMaxSize()
            )

        }
    }


}

/*
@Composable
private fun ReminderList(
    reminders: List<Reminder>
){
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ){
        items(reminders) {item ->
            ReminderListItem(
                reminder = item,
                onClick={},
                modifier = Modifier.fillParentMaxWidth()
            )
        }
    }
}

@Composable
private fun ReminderListItem(
    reminder: Reminder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    ConstraintLayout(modifier = modifier.clickable { onClick() }) {
        val (divider, reminderMessage, reminderTime) = createRefs()
        Divider(
            Modifier.constrainAs(divider){
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        //title
        Text(

            //text = reminder.reminderTitle,
            text = reminder.reminderMessage,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(reminderMessage) {
                linkTo(
                    start = parent.start,
                    end = parent.end,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        // date
        Text(
            //text = reminder.reminderTime.toString(),
            text="",
            maxLines = 1,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(reminderTime) {
                linkTo(
                    start = reminderMessage.end,
                    end = reminderMessage.start,
                    startMargin = 8.dp,
                    endMargin = 16.dp,
                    bias = 0f // float this towards the start. this was is the fix we needed
                )
                top.linkTo(reminderMessage.bottom, 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
            }
        )


    }
}
*/

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
            IconButton( onClick = {navController.navigate("authentication") } ) {
                Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = stringResource(R.string.search))
            }
            IconButton( onClick = {navController.navigate("profile") } ) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = stringResource(R.string.account))
            }
        }
    )
}

