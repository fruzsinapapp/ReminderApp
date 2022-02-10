package com.example.reminderapp.ui.home.reminderMessages

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.material.icons.outlined.CheckCircle
//import androidx.compose.material.icons.filled.Update

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.codemave.mobilecomputing.util.viewModelProviderFactoryOf
import com.example.reminderapp.R
import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.ui.edit.Edit
import com.example.reminderapp.ui.reminder.ReminderViewModel
import com.example.reminderapp.ui.reminder.ReminderViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun ReminderMessages(
    modifier: Modifier = Modifier,
    navController: NavController,
    //selectedReminder: Reminder

){






    val viewModel: ReminderMessagesViewModel = viewModel(
        key = "reminder_list",
        factory = viewModelProviderFactoryOf{ReminderMessagesViewModel()}
    )




    val viewState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    //val selectedReminder = viewState.selectedReminder


    //val selectedReminder = viewState.selectedReminder

    Column(modifier = modifier ) {
        ReminderList(
            list = viewState.reminders,
            coroutineScope = coroutineScope,
            viewModel = viewModel(),
            navController = navController

        )
    }
}

@Composable
 fun ReminderList(
    list: List<Reminder>,
    coroutineScope: CoroutineScope,
    viewModel: ReminderMessagesViewModel,
    navController: NavController
) {


    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ){


        items(list) {item ->
        val reminderId:String = item.reminderId.toString()

            ReminderListItem(
                coroutineScope= coroutineScope,
                reminder = item,
                onClick={
                    navController.navigate("edit/${reminderId}")
                    println(reminderId)
                        },
                modifier = Modifier.fillParentMaxWidth(),
                viewModel = viewModel
            )
        }
    }
}



@Composable
 fun ReminderListItem(
    coroutineScope: CoroutineScope,
    reminder: Reminder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReminderMessagesViewModel
){


    ConstraintLayout(modifier = modifier.clickable { onClick() }) {
        val (divider, reminderMessage, reminderTime, icon1, icon2) = createRefs()
        Divider(
            Modifier.constrainAs(divider){
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }

        )


        //Edit(reminderId = 2,modifier = modifier)


        //title
        Text(

            //text = reminder.reminderTitle,
            text = reminder.reminderMessage,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(reminderMessage) {
                linkTo(
                    start = parent.start,
                    end = icon1.start,
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
            text=reminder.reminderSeen.toString(),
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(reminderTime) {
                linkTo(
                    start = reminderMessage.end,
                    end = icon1.start,
                    startMargin = 8.dp,
                    endMargin = 16.dp,
                    bias = 0f // float this towards the start. this was is the fix we needed
                )
                top.linkTo(parent.top, 10.dp)
                //bottom.linkTo(parent.bottom, 10.dp)
            }
        )





        IconButton(
            onClick = {
                coroutineScope.launch {
                    viewModel.deleteReminder(reminder)
                }
            },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)

                .constrainAs(icon1) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }

        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(R.string.delete)
            )
        }
        IconButton(
            onClick = {
                coroutineScope.launch {
                    viewModel.updateSeen(true, reminder.reminderId)
                }

            },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)

                .constrainAs(icon2) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(icon1.start)
                }

        ) {
            Icon(
                imageVector = if(reminder.reminderSeen) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle ,
                contentDescription = stringResource(R.string.seen)

            )
        }


/*
        IconButton(
            onClick = {
                coroutineScope.launch {
                    viewModel.updateReminder(reminder)
                }
            },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)

                .constrainAs(icon) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    start.linkTo(icon.end)
                }


        ) {
            Icon(
                imageVector = Icons.Filled.Update,
                contentDescription = stringResource(R.string.update)
            )
        }
 */


    }
}

private fun Long.toDateString(): String {
    return SimpleDateFormat("dd. MM. yyyy", Locale.getDefault()).format(Date(this))
}



