package com.example.reminderapp.ui.maps

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.reminderapp.util.rememberMapViewWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun ReminderLocationMap(
    navController: NavController,
    onBackPress: () -> Unit
) {
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
            Text(text = "Create reminder")
        }
        //display map
        AndroidView({mapView}) { mapView ->
            coroutineScope.launch {
                //async --> coroutine
                val map = mapView.awaitMap()
                //map can be zoomed
                map.uiSettings.isZoomControlsEnabled = true
                //current location
                val location = LatLng(65.06, 25.47)

                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(location, 10f)
                )

                val markerOptions = MarkerOptions()
                    .title("Welcome to Oulu")
                    .position(location)
                map.addMarker(markerOptions)

                setMapLongClick(map = map, navController = navController)


            }
        }
    }
}

private fun setMapLongClick(
    map: GoogleMap,
    navController: NavController
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