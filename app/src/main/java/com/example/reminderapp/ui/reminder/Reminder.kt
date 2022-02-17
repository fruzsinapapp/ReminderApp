package com.example.reminderapp.ui.reminder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reminderapp.R
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import android.widget.CalendarView

import android.widget.CalendarView.OnDateChangeListener
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import com.example.reminderapp.Graph

object WithNotification{
    const val with = "With notification"
    const val without = "Without notification"
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Reminder(
    onBackPress: () -> Unit,
    viewModel: ReminderViewModel = viewModel()
) {
    //val context = Graph.appContext
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val message = rememberSaveable { mutableStateOf("") }
    val withOrWithout = remember{ mutableStateOf("")}


    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
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

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = message.value,
                    onValueChange = { message.value = it },
                    label = { Text(text = "Reminder message")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))

                /**
                 *
                 * Date picker
                 */
                val year: Int
                val month: Int
                val day: Int

                val calendar = Calendar.getInstance()

                year = calendar.get(Calendar.YEAR)
                month = calendar.get(Calendar.MONTH)
                day = calendar.get(Calendar.DAY_OF_MONTH)

                val dateSetListener = object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                           dayOfMonth: Int) {
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, monthOfYear)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }
                }

                val datePickerDialog = DatePickerDialog(
                    context,
                    dateSetListener, year, month, day
                )

                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    onClick = {
                        datePickerDialog.show()
                    }) {
                    Text(text="Open date-picker")
                }

                /**
                 * Time picker
                 */
                val hour = calendar[Calendar.HOUR_OF_DAY]
                val minute = calendar[Calendar.MINUTE]
                val timeSetListener = object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(view: TimePicker, hour: Int, minute: Int) {
                        calendar.set(Calendar.HOUR_OF_DAY, hour)
                        calendar.set(Calendar.MINUTE, minute)

                    }
                }
                val timePickerDialog = TimePickerDialog(
                    context,
                    timeSetListener, hour, minute, true
                )

                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    onClick = {
                        timePickerDialog.show()
                    }) {
                    Text(text="Open time-picker")
                }

                Spacer(modifier = Modifier.size(20.dp))



                //Radio button
                Text("Would you like to have a notification for you reminder?")
                Spacer(modifier = Modifier.size(20.dp))
                RadioButton(
                    selected = withOrWithout.value==WithNotification.with,
                    onClick = {withOrWithout.value = WithNotification.with}
                )
                Text("With")
                Spacer(modifier = Modifier.size(20.dp))
                RadioButton(
                    selected = withOrWithout.value==WithNotification.without,
                    onClick = {withOrWithout.value = WithNotification.without}
                )
                Text("Without")
                Button(
                    enabled = true,
                    onClick = {
                        coroutineScope.launch {
                            viewModel.saveReminder(
                                com.example.reminderapp.data.entity.Reminder(
                                    reminderMessage = message.value,
                                    reminderTime = calendar.timeInMillis,
                                    reminderSeen = false,
                                    creationTime = Calendar.getInstance().timeInMillis,
                                    withNotification = true
                                )
                            )
                        }
                        onBackPress()

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                ){
                    Text("Save reminder")
                }

            }
        }
    }
}



