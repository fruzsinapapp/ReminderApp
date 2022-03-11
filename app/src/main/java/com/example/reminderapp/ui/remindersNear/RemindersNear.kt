package com.example.reminderapp.ui.remindersNear

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.reminderapp.R
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
        Image(
            modifier = Modifier,
            painter = painterResource(R.drawable.background1),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center
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
                Text(text = "Back")
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.navigate("map") }

                ) {
                    Text(text = "Select location")
                }
                Spacer(modifier = Modifier.size(20.dp))

                if (latlng == null) {
                    Text(
                        text = "Latitude of position: not selected yet\nLongitude position: not selected yet"
                    )
                } else {
                    Text(
                        text = "Latitude: ${latlng.latitude} \nLongitude: ${latlng.longitude}"
                    )

                }
                Spacer(modifier = Modifier.size(20.dp))
                Button(
                    //onClick = { navController.navigate("selectedReminders/${latlng?.latitude.toString()},${latlng?.longitude.toString()}") }
                    onClick = { navController.navigate("selectedReminders") }
                ) {
                    Text(text = "Show reminders")
                }
            }
        }
    }
}