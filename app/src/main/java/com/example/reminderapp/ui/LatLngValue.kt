package com.example.reminderapp.ui

object LatLngValue {

     var latitude:Double = 0.0
     var longitude:Double = 0.0

    var fbkey:String = ""



    fun setKey(value: String){
        this.fbkey = value
    }
    fun getKey(): String{
        return fbkey
    }

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