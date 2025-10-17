package com.example.testrikandmorty.data.database

import androidx.room.TypeConverter
import com.example.testrikandmorty.data.model.Location
import com.example.testrikandmorty.data.model.Origin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    
    @TypeConverter
    fun fromOrigin(origin: Origin): String {
        return Gson().toJson(origin)
    }
    
    @TypeConverter
    fun toOrigin(originString: String): Origin {
        return Gson().fromJson(originString, Origin::class.java)
    }
    
    @TypeConverter
    fun fromLocation(location: Location): String {
        return Gson().toJson(location)
    }
    
    @TypeConverter
    fun toLocation(locationString: String): Location {
        return Gson().fromJson(locationString, Location::class.java)
    }
    
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }
    
    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
}




