package com.example.reminderapp.ui.reminder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.launch
import java.util.*

import android.widget.TimePicker
import androidx.navigation.NavController
import com.example.reminderapp.ui.maps.MapsActivity
import com.google.android.gms.maps.model.LatLng

object WithNotification{
    const val with = "With notification"
    const val without = "Without notification"
}
object WithLocation{
    const val with = "With location"
    const val without = "Without location"
}


@Composable
fun Reminder(
    navController: NavController,
    onBackPress: () -> Unit,
    viewModel: ReminderViewModel = viewModel()
) {
    //val context = Graph.appContext
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val message = rememberSaveable { mutableStateOf("") }
    val withOrWithout = remember{ mutableStateOf("")}
    val withOrWithoutLocation = remember{ mutableStateOf("")}

    val xLocation = rememberSaveable { mutableStateOf("") }
    val yLocation = rememberSaveable { mutableStateOf("") }


    val latlng = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<LatLng>("location_data") //same key!
        ?.value

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
                if (latlng==null){
                    OutlinedButton(
                        //onClick = { navController.navigate("map") }
                    onClick = {context.startActivity(Intent(context,MapsActivity::class.java))}
                    ) {
                        Text(text="Reminder location")
                    }
                } else {
                    Text(
                        text = "Lat: ${latlng.latitude}, \nLng: ${latlng.longitude}"
                    )

                }

                Spacer(modifier = Modifier.height(20.dp))
                //Radio button
                Text("Would you like to have a notification for you reminder?")
                Spacer(modifier = Modifier.size(20.dp))
                Row{

                    RadioButton(
                        selected = withOrWithout.value==WithNotification.with,
                        onClick = {withOrWithout.value = WithNotification.with}
                    )
                    Text("Yes")
                    Spacer(modifier = Modifier.size(20.dp))
                    RadioButton(
                        selected = withOrWithout.value==WithNotification.without,
                        onClick = {withOrWithout.value = WithNotification.without}
                    )
                    Text("No")
                }
                Text("Would you like to have a location for you reminder?")
                Spacer(modifier = Modifier.size(20.dp))
                Row{

                    RadioButton(
                        selected = withOrWithoutLocation.value==WithLocation.with,
                        onClick = {withOrWithoutLocation.value = WithLocation.with}
                    )
                    Text("Yes")
                    Spacer(modifier = Modifier.size(20.dp))
                    RadioButton(
                        selected = withOrWithoutLocation.value==WithLocation.without,
                        onClick = {withOrWithoutLocation.value = WithLocation.without}
                    )
                    Text("No")
                }

                Spacer(modifier = Modifier.size(20.dp))

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

                Spacer(modifier = Modifier.size(20.dp))

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

                Spacer(modifier = Modifier.size(20.dp))

                Button(
                    onClick = {
                        timePickerDialog.show()
                    }) {
                    Text(text="Open time-picker")
                }

                Spacer(modifier = Modifier.size(20.dp))






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
                                    withNotification = withOrWithout.value== WithNotification.with,
                                    locationX = latlng?.latitude,
                                    locationY = latlng?.longitude,
                                    withLocation = withOrWithoutLocation.value==WithLocation.with
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




