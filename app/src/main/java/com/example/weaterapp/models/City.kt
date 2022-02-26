package com.example.weaterapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cities",
    indices = [Index(value = ["city_name", "lat", "lon"], unique = true)]
)
data class City(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val lat: Double,
    val lon: Double,
    @ColumnInfo(name = "city_name")
    val cityName: String
)