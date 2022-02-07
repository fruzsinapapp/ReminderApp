
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
import com.codemave.mobilecomputing.util.viewModelProviderFactoryOf
import com.example.reminderapp.R
import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.ui.reminder.ReminderViewModel
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun ReminderMessages(
    modifier: Modifier = Modifier
){
    val viewModel: ReminderMessagesViewModel = viewModel(
        key = "reminder_list",
        factory = viewModelProviderFactoryOf{ReminderMessagesViewModel()}
    )
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

private fun Date.formatToString(): String {
    return SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(this)
}


