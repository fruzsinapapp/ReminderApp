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
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.example.reminderapp.data.entity.Reminder
import com.example.reminderapp.ui.GeofenceReceiver2
import com.example.reminderapp.ui.LatLngValue
import com.example.reminderapp.ui.MapActivity
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database

import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener





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


/*
    val database = Firebase.database("https://reminderapp-342212-default-rtdb.europe-west1.firebasedatabase.app/")

    val reference = database.reference

    val key = reference.push().key

 */



/*
    val latlng = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<LatLng>("location_data") //same key!
        ?.value
*/
    val latitude=LatLngValue.latitude
    val longitude=LatLngValue.longitude


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
                    label = { Text(text = " message")},
                    modifier = Modifier.fillMaxWidth()
                )


                if (latitude==0.0 && longitude ==0.0){
                    OutlinedButton(
                        onClick = { context.startActivity(Intent(context, MapActivity::class.java))}//{ navController.navigate("map/${key}") }
                    ) {
                        Text(text="Reminder location")
                    }
                } else {
                    Text(
                        text = "Lat: ${latitude} Long: ${longitude}"
                                //text = "Lat: ${latlng.latitude}, \nLng: ${latlng.longitude}"
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

                /**
                 *
                 * Date picker
                 */
                /**
                 *
                 * Date picker
                 */
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

                /**
                 * Time picker
                 */
                /**
                 * Time picker
                 */
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
                                Reminder(
                                    reminderMessage = message.value,
                                    reminderTime = calendar.timeInMillis,
                                    reminderSeen = false,
                                    creationTime = Calendar.getInstance().timeInMillis,
                                    withNotification = withOrWithout.value== WithNotification.with,
                                    locationX = LatLngValue.latitude,//latlng?.latitude,
                                    locationY = LatLngValue.longitude,//latlng?.longitude,
                                    withLocation = withOrWithoutLocation.value==WithLocation.with
                                )
                            )
                            /*
                            val reminderToSave= Reminder(
                                reminderMessage = message.value,
                                reminderTime = calendar.timeInMillis,
                                reminderSeen = false,
                                creationTime = Calendar.getInstance().timeInMillis,
                                withNotification = withOrWithout.value== WithNotification.with,
                                locationX = latlng?.latitude,
                                locationY = latlng?.longitude,
                                withLocation = withOrWithoutLocation.value==WithLocation.with
                            )

                             */

                            //val data = reference.push().child("location").setValue(latlng)

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




