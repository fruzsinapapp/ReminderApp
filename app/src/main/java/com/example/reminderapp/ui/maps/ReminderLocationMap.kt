package com.example.reminderapp.ui.maps

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.reminderapp.Graph
import com.example.reminderapp.R
import com.example.reminderapp.ui.MainActivity
import com.example.reminderapp.util.rememberMapViewWithLifecycle
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

@Composable
fun ReminderLocationMap(
    navController: NavController,
    onBackPress: () -> Unit
) {
    val context = LocalContext.current
    lateinit var GMap: GoogleMap


    val mapView = rememberMapViewWithLifecycle()
    val coroutineScope = rememberCoroutineScope()


    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .padding(bottom = 30.dp)
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
        //display map
        AndroidView({mapView}) { mapView ->
            coroutineScope.launch {
                //async --> coroutine
                val map = mapView.awaitMap()
                //GMap = mapView.awaitMap()



                //map can be zoomed
                map.uiSettings.isZoomControlsEnabled = true
                //GMap.uiSettings.isZoomControlsEnabled = true
                //current location
                val location = LatLng(65.06, 25.47)

                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(location, 10f)
                )

                val markerOptions = MarkerOptions()
                    .title("Welcome to Oulu")
                    .position(location)
                map.addMarker(markerOptions)

                setMapLongClick(map = map, navController = navController, context = context)


            }
        }
    }
}

private fun setMapLongClick(
    map: GoogleMap,
    navController: NavController,
    context: Context

) {

    //snipet
    map.setOnMapLongClickListener { latlng ->
        val snippet = String.format(
            Locale.getDefault(),
            "Lat: %1$.2f, Lng: %2$.2f",
            latlng.latitude,
            latlng.longitude
        )

        map.addMarker(
            MarkerOptions().position(latlng).title("Reminder location").snippet(snippet)
        )
            //when we go back we can get the data from the map from here, in the reminder
            .apply {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("location_data", latlng)
        }



    }
}

