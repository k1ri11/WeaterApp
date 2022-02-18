package com.example.weaterapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weaterapp.models.City

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCity(city: City)

    @Query("SELECT * FROM cities")
    fun getAllCities(): LiveData<List<City>>

    @Delete
    suspend fun deleteCity(city: City)
}