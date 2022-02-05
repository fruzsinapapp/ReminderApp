package com.example.reminderapp.ui.reminder

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reminderapp.R
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*


@Composable
fun Reminder(
    onBackPress: () -> Unit,
    viewModel: ReminderViewModel = viewModel()
) {
    val title = rememberSaveable { mutableSetOf("")}



    val coroutineScope = rememberCoroutineScope()
    val message = rememberSaveable { mutableStateOf("")}
    val creatorId = rememberSaveable { mutableStateOf("")}

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
            Image(
                modifier = Modifier,
                painter = painterResource(R.drawable.background1),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(text="Create a new reminder")
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ){
                OutlinedTextField(
                    value = message.value,
                    onValueChange={message.value = it},
                    label={Text(text="Reminder message")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = creatorId.value,
                    onValueChange={message.value = it},
                    label={Text(text="ID of the creator")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    enabled = true,
                    onClick = {
                        coroutineScope.launch {
                            viewModel.saveReminder(
                                com.example.reminderapp.data.entity.Reminder(
                                    message = message.value,
                                    creatorId = creatorId.value
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(50.dp)
                ){
                    Text("Save reminder")
                }
            }
        }
    }
}