package com.example.reminderapp.ui.edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.reminderapp.util.viewModelProviderFactoryOf
import com.example.reminderapp.R
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.launch
import java.util.*


@Composable
fun Edit(
    navController: NavController,
    reminderId: String,
    onBackPress: () -> Unit

) {
    val selectedReminder=reminderId.toLong()

    val viewModel: EditViewModel = viewModel(
        key = "edit",
        factory = viewModelProviderFactoryOf{ EditViewModel(selectedReminder) }
    )
    val viewState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val newMessage = remember { mutableStateOf("") }
    val newTime = remember { mutableStateOf("") }
    val date= remember{ mutableStateOf("")}

    val context = LocalContext.current



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


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = newMessage.value,
                    onValueChange = { newMessage.value = it },
                    label = { Text(text = "New reminder message")},
                    modifier = Modifier.fillMaxWidth()
                )

                /**
                 *
                 * Time picker
                 */
                val calendar = Calendar.getInstance()
                val hour = calendar[Calendar.HOUR_OF_DAY]
                val minute = calendar[Calendar.MINUTE]

                val time = remember{ mutableStateOf("")}

                val timePickerDialog = TimePickerDialog(
                    context,
                    {_, hour : Int, minute : Int ->
                        time.value = "$hour:$minute"

                    }, hour, minute, true
                )
                newTime.value = time.value.toString()

                Text(text="Selected time: ${time.value}")

                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {
                        timePickerDialog.show()
                    }) {
                    Text(text="Open picker")
                }
                Spacer(modifier = Modifier.size(20.dp))



                /**
                 *
                 * Date picker
                 */
                val year: Int
                val month: Int
                val day: Int

                //val calendar = Calendar.getInstance()

                year = calendar.get(Calendar.YEAR)
                month = calendar.get(Calendar.MONTH)
                day = calendar.get(Calendar.DAY_OF_MONTH)




                val datePickerDialog = DatePickerDialog(
                    context,
                    { _: DatePicker, year : Int, month  : Int, dayOfMonth : Int->
                        val monthCorr = month+1
                        date.value = "$dayOfMonth.$monthCorr.$year"

                    }, year, month, day
                )

                Text(text="Selected time: ${date.value}")
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {
                        datePickerDialog.show()
                    }) {
                    Text(text="Open picker")
                }



                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    enabled = true,
                    onClick = {
                        coroutineScope.launch {
                            viewModel.updateTest(newMessage.value,newTime.value,selectedReminder)

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


/**
 * Not used
 */

@Composable
fun ShowTimePicker(context: Context){
    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    val time = remember{ mutableStateOf("")}

    val timePickerDialog = TimePickerDialog(
        context,
        {_, hour : Int, minute : Int ->
            time.value = "$hour:$minute"

        }, hour, minute, true
    )
    //::::::::::::::
    val newTimeTest = time.value.toString()

        Text(text="Selected time: ${time.value}")

        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = {
                timePickerDialog.show()
            }) {
            Text(text="Open picker")
        }
        Spacer(modifier = Modifier.size(20.dp))


}

@Composable
fun ShowDatePicker(context: Context){
    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()

    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)

    val date= remember{ mutableStateOf("")}


    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year : Int, month  : Int, dayOfMonth : Int->
            val monthCorr = month+1
            date.value = "$dayOfMonth.$monthCorr.$year"

        }, year, month, day
    )

        Text(text="Selected time: ${date.value}")
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = {
                datePickerDialog.show()
            }) {
            Text(text="Open picker")
        }

}


