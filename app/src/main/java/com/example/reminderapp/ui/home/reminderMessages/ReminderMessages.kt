package com.example.reminderapp.ui.home.reminderMessages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reminderapp.R
import com.example.reminderapp.data.entity.Reminder
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ReminderMessages(
    modifier: Modifier = Modifier
){
    val viewModel: ReminderMessagesViewModel = viewModel()
    val viewState by viewModel.state.collectAsState()

    Column(modifier = modifier ) {
        ReminderList(
            list = viewState.reminders
        )
    }
}

@Composable
private fun ReminderList(
    list: List<Reminder>
) {
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ){
        items(list) {item ->
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

}