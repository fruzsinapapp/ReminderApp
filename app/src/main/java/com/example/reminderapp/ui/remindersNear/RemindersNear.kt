package com.example.reminderapp.ui.remindersNear

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.reminderapp.ui.home.HomeViewModel
import com.example.reminderapp.ui.maps.MapsActivity
import com.example.reminderapp.ui.maps.SelectedSpot
import com.google.accompanist.insets.systemBarsPadding
import com.google.android.gms.maps.model.LatLng

@Composable
fun RemindersNear(
    viewModel: RemindersNearViewModel = viewModel(),
    navController: NavController,
    onBackPress: () -> Unit
) {
val context = LocalContext.current
    val latlng = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<LatLng>("location_data") //same key!
        ?.value

if(latlng != null){
    SelectedSpot.latitude= latlng.latitude
    SelectedSpot.longitude=latlng.longitude
}


    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar {
                IconButton(
                    onClick = onBackPress
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(text = "First screen")
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                if (latlng == null) {


                    OutlinedButton(
                        onClick = { navController.navigate("map") }

                    ) {
                        Text(text = "Select location")
                    }
                } else {
                    Text(
                        text = "Lat: ${latlng.latitude}, \nLng: ${latlng.longitude}"
                    )

                }


                OutlinedButton(
                    //onClick = { navController.navigate("selectedReminders/${latlng?.latitude.toString()},${latlng?.longitude.toString()}") }
                    onClick = { navController.navigate("selectedReminders") }


                ) {
                    Text(text = "Show reminders")
                }
            }


        }
    }
}