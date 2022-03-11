package com.example.reminderapp.ui.maps

object SelectedSpot {

    var latitude:Double = 0.0
    var longitude:Double = 0.0


    fun setLat(value: Double){
        this.latitude = value
    }
    fun getLat(): Double{
        return latitude
    }

    fun setLong(value: Double){
        this.longitude = value
    }
    fun getLong(): Double{
        return longitude
    }
}