package com.example.reminderapp.ui.edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import com.codemave.mobilecomputing.util.viewModelProviderFactoryOf
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

    val newMessage = rememberSaveable { mutableStateOf("") }
    val newTime = rememberSaveable { mutableStateOf("") }
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
                Spacer(modifier = Modifier.height(10.dp))
                //Text(newtime.toString())

                Button(
                    enabled = true,
                    onClick = {
                        val cal = Calendar.getInstance()
                        val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                            cal.set(Calendar.HOUR_OF_DAY, hour)
                            cal.set(Calendar.MINUTE,minute)
                        }
                        TimePickerDialog(context,timeSetListener,cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
                        //Text(SimpleDateFormat("HH:mm").format(cal.time))

                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                ){
                    Text("Choose time")
                }




                Button(
                    enabled = true,
                    onClick = {
                        val c = Calendar.getInstance()
                        val year = c.get(Calendar.YEAR)
                        val month = c.get(Calendar.MONTH)
                        val day = c.get(Calendar.DAY_OF_MONTH)



                        val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        }, year, month, day)
                        dpd.show()

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                ){
                    Text("Choose date")
                }


                /*
                OutlinedTextField(
                    value = newTime.value,
                    onValueChange = { newTime.value = it },
                    label = { Text(text = "New reminder time")},
                    modifier = Modifier.fillMaxWidth()
                )

                 */

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


