package com.example.reminderapp.ui.maps

object LatiLongi {

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
/*
    fun getlati() {
        return this.lati
    }

    fun setlati(value: Double){
        this.lati = value
    }

    fun getlongi() {
        this.lati
    }

    fun setlongi(value: Double){
        this.lati = value
    }
*/
