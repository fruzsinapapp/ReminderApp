package com.example.reminderapp.ui.maps

object LatiLongi {

    var lati: Double = 0.0
    var longi : Double = 0.0

    fun getlat() {
        this.lati
    }

    fun setlat(value: Double){
        this.lati = value
    }

    fun getlongi() {
        this.lati
    }

    fun setlongi(value: Double){
        this.lati = value
    }

    var checkEntered: Boolean = false

    fun setcheckEntered(value: Boolean){
        this.checkEntered = value
    }

    fun getcheckEntered() {
        this.checkEntered
    }
}