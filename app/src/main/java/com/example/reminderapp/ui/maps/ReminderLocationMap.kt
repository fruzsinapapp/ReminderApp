package com.example.reminderapp.ui.maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.reminderapp.Graph
import com.example.reminderapp.util.rememberMapViewWithLifecycle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch
import java.util.*



const val GEOFENCE_RADIUS = 200
const val GEOFENCE_ID = "REMINDER_GEOFENCE_ID"
const val GEOFENCE_EXPIRATION = 10 * 24 * 60 * 60 * 1000 // 10 days
const val GEOFENCE_DWELL_DELAY =  10 * 1000 // 10 secs // 2 minutes
const val GEOFENCE_LOCATION_REQUEST_CODE = 12345
const val CAMERA_ZOOM_LEVEL = 13f
const val LOCATION_REQUEST_CODE = 123




@Composable
fun ReminderLocationMap(
    navController: NavController
) {
    val mapView = rememberMapViewWithLifecycle()
    val coroutineScope = rememberCoroutineScope()


//geofencing
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var geofencingClient: GeofencingClient

    val context = LocalContext.current
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    geofencingClient = LocationServices.getGeofencingClient(context)

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .padding(bottom = 30.dp)
    ) {
        //display map
        AndroidView({mapView}) { mapView ->
            coroutineScope.launch {
                //async --> coroutine
                val map = mapView.awaitMap()
                //map can be zoomed
                map.uiSettings.isZoomControlsEnabled = true


/*
                //geofencing permission check
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return@launch
                }
                map.isMyLocationEnabled=true
                fusedLocationClient.lastLocation.addOnSuccessListener {
                        if (it != null) {
                            with(map) {
                                val latLng = LatLng(it.latitude, it.longitude)
                                moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM_LEVEL))
                            }
                        } else {
                            with(map) {
                                moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        LatLng(65.01355297927051, 25.464019811372978),
                                        CAMERA_ZOOM_LEVEL
                                    )
                                )
                            }
                        }
                    }

 */





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

        //for geofence
        map.addCircle(
            CircleOptions()
                .center(latlng)
                .strokeColor(android.graphics.Color.argb(50, 70, 70, 70))
                .fillColor(android.graphics.Color.argb(70, 150, 150, 150))
                .radius(GEOFENCE_RADIUS.toDouble())
        )


    }
}




/*
//geofencing
private fun isLocationPermissionGranted(
    context: Context
) : Boolean {
    return ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
        Graph.appContext, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

 */