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

/*
const val GEOFENCE_RADIUS = 200
const val GEOFENCE_ID = "REMINDER_GEOFENCE_ID"
const val GEOFENCE_EXPIRATION = 10 * 24 * 60 * 60 * 1000 // 10 days
const val GEOFENCE_DWELL_DELAY =  10 * 1000 // 10 secs // 2 minutes
const val GEOFENCE_LOCATION_REQUEST_CODE = 12345
const val CAMERA_ZOOM_LEVEL = 13f
const val LOCATION_REQUEST_CODE = 123
*/


@Composable
fun ReminderLocationMap(
    navController: NavController,
    onBackPress: () -> Unit
) {
    val context = LocalContext.current
    lateinit var GMap: GoogleMap


    var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    var geofencingClient: GeofencingClient = LocationServices.getGeofencingClient(context)




    val mapView = rememberMapViewWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    if (!isLocationPermissionGranted(context)) {
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }


        ActivityCompat.requestPermissions(
            context as Activity,
            permissions.toTypedArray(),
            LOCATION_REQUEST_CODE
        )
    } else {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return

        }
        GMap.isMyLocationEnabled = true
        //GMap.isMyLocationEnabled = true

        // Zoom to last known location
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                with(GMap) {
                    val latLng = LatLng(it.latitude, it.longitude)
                    moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM_LEVEL))
                }
            } else {
                with(GMap) {
                    moveCamera(
                        com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(
                            LatLng(65.01355297927051, 25.464019811372978),
                            CAMERA_ZOOM_LEVEL
                        )
                    )
                }
            }
        }
    }

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
                //val map = mapView.awaitMap()
                GMap = mapView.awaitMap()










                //map can be zoomed
                //map.uiSettings.isZoomControlsEnabled = true
                GMap.uiSettings.isZoomControlsEnabled = true
                //current location
                val location = LatLng(65.06, 25.47)

                GMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(location, 10f)
                )

                val markerOptions = MarkerOptions()
                    .title("Welcome to Oulu")
                    .position(location)
                GMap.addMarker(markerOptions)

                setMapLongClick(map = GMap, navController = navController,geofencingClient,context)


            }
        }
    }
}

private fun setMapLongClick(
    map: GoogleMap,
    navController: NavController,
    geofencingClient: GeofencingClient,
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


        map.addCircle(
            CircleOptions()
                .center(latlng)
                .strokeColor(android.graphics.Color.argb(50, 70, 70, 70))
                .fillColor(android.graphics.Color.argb(70, 150, 150, 150))
                .radius(GEOFENCE_RADIUS.toDouble())
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, CAMERA_ZOOM_LEVEL))

        val key = "test_key"
        createGeoFence(latlng, key, geofencingClient,context)
        ShowNotification(context,"TEST")

    }
}

fun ShowNotification(context: Context?, message: String) {
    val CHANNEL_ID = "REMINDER_NOTIFICATION_CHANNEL"
    var notificationId = 1589
    notificationId += Random(notificationId).nextInt(1, 30)

    val notificationBuilder = NotificationCompat.Builder(context!!.applicationContext, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle(context.getString(R.string.app_name))
        .setContentText(message)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(message)
        )
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.app_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context.getString(R.string.app_name)
        }
        notificationManager.createNotificationChannel(channel)
    }
    notificationManager.notify(notificationId, notificationBuilder.build())
}




private fun createGeoFence(location: LatLng, key: String, geofencingClient: GeofencingClient, context: Context) {
    val geofence = Geofence.Builder()
        //.setRequestId(GEOFENCE_ID)
        .setCircularRegion(location.latitude, location.longitude, GEOFENCE_RADIUS.toFloat())
        .setExpirationDuration(GEOFENCE_EXPIRATION.toLong())
        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_DWELL)
        .setLoiteringDelay(GEOFENCE_DWELL_DELAY)
        .build()

    val geofenceRequest = GeofencingRequest.Builder()
        .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        .addGeofence(geofence)
        .build()

    val intent = Intent(context, GeofenceReceiver::class.java)
        .putExtra("key", key)
        .putExtra("message", "Geofence alert - ${location.latitude}, ${location.longitude}")

    val pendingIntent = PendingIntent.getBroadcast(
        context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                GEOFENCE_LOCATION_REQUEST_CODE
            )
        } else {
            geofencingClient.addGeofences(geofenceRequest, pendingIntent)
        }
    } else {
        geofencingClient.addGeofences(geofenceRequest, pendingIntent)
    }
}

@Override
fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray,
    map: GoogleMap,
    context: Context
) {
    if (requestCode == GEOFENCE_LOCATION_REQUEST_CODE) {
        if (permissions.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                context,
                "This application needs background location to work on Android 10 and higher",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    if (requestCode == LOCATION_REQUEST_CODE) {
        if (
            grantResults.isNotEmpty() && (
                    grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                            grantResults[1] == PackageManager.PERMISSION_GRANTED)
        ) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            map.isMyLocationEnabled = true
            //onMapReady(map)
        } else {
            Toast.makeText(
                context,
                "The app needs location permission to function",
                Toast.LENGTH_LONG
            ).show()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (grantResults.isNotEmpty() && grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    context,
                    "This application needs background location to work on Android 10 and higher",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}





private fun isLocationPermissionGranted(context: Context) : Boolean {
    return ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}