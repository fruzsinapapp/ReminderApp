package com.example.reminderapp.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.reminderapp.data.entity.Reminder
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class GeofenceReceiver2 : BroadcastReceiver() {
    lateinit var key: String
    lateinit var text: String

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            val geofencingEvent = GeofencingEvent.fromIntent(intent!!)
            val geofencingTransition = geofencingEvent.geofenceTransition

            if (geofencingTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofencingTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
                // Retrieve data from intent
                if (intent != null) {
                    key = intent.getStringExtra("key")!!
                    text = intent.getStringExtra("message")!!
                }
/*
                val firebase = Firebase.database
                val reference = firebase.getReference("reminders")



 */

                val database = Firebase.database("https://reminderapp-342212-default-rtdb.europe-west1.firebasedatabase.app/")
                val reference = database.reference



                val reminderListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val reminder = snapshot.getValue<LatLng>()
                        if (reminder != null) {

                            MapActivity
                                .showNotification(
                                    context.applicationContext,
                                    "Location"
                                )

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        println("reminder:onCancelled: ${error.details}")
                    }

                }
                val child = reference.child(key)
                child.addValueEventListener(reminderListener)

                // remove geofence
                val triggeringGeofences = geofencingEvent.triggeringGeofences
                MapActivity.removeGeofences(context, triggeringGeofences)
            }
        }
    }
}