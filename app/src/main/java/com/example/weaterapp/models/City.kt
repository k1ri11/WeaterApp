package com.example.weaterapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "cities")
data class City(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val cityName: String
)