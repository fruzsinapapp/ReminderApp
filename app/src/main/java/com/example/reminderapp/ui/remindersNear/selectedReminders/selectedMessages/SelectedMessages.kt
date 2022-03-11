package com.example.reminderapp.ui.remindersNear.selectedReminders.selectedMessages

import android.location.Location
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.reminderapp.R
import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.ui.allReminders.AllReminderMessagesViewModel
import com.example.reminderapp.ui.home.reminderMessages.ReminderMessagesViewModel
import com.example.reminderapp.ui.maps.SelectedSpot
import com.example.reminderapp.util.viewModelProviderFactoryOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.Collections.copy

@Composable
fun SelectedMessages(
    modifier: Modifier = Modifier,
    navController: NavController,
    onBackPress: () -> Unit
    //currentTime : Long
){
    val viewModel: SelectedMessagesViewModel = viewModel(
        key = "selected",
        factory = viewModelProviderFactoryOf{ SelectedMessagesViewModel() }
    )

    val viewState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val x2 = SelectedSpot.latitude
    val y2 = SelectedSpot.longitude

    Column(modifier = modifier ) {

        val list2 = viewState.reminders
        for (i in list2){
            if (i.locationX!=null && i.locationY != null){
                i.distance= calculateDistance(i.locationX!!.toDouble(),
                    i.locationY!!.toDouble(),x2,y2).toDouble()
            }
            else{
                i.distance= 5.0
            }

        }
        val list3 = list2.filter { it.distance < 200 }

        TopAppBar {
            IconButton(
                onClick = onBackPress
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
            Text(text = "Back")
        }
        ReminderList(
            list=list3,
            //list = viewState.reminders,
            coroutineScope = coroutineScope,
            viewModel = viewModel(),
            navController = navController
        )


    }
}


fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {
    val results = FloatArray(1)
    Location.distanceBetween(lat1, lng1, lat2, lng2, results)
    // distance in meter
    return results[0]
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
        val (divider, reminderMessage, reminderTime, icon1, icon2,reminderNot) = createRefs()

        Divider(
            Modifier.constrainAs(divider){
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }

        )

        //message
        Text(
            text = reminder.reminderMessage,
            maxLines = 2,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(reminderMessage) {
                linkTo(
                    start = parent.start,
                    end = icon2.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )


        // time
        Text(
            text=getTimeStamp(reminder.reminderTime),
            maxLines = 2,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(reminderTime) {
                linkTo(
                    start = reminderMessage.end,
                    end = icon2.start,
                    startMargin = 8.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, 10.dp)
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
    }
}


fun getTimeStamp(timeInMillis: Long): String {
    var date: String? = null
    val formatter = SimpleDateFormat("MM.dd. HH:mm")
    date = formatter.format(Date(timeInMillis))
    return date
}

