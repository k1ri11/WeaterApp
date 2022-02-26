package com.example.weaterapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weaterapp.models.City

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCity(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCities(new_cities :List<City>)

    @Query("SELECT * FROM cities")
    fun getAllCities(): LiveData<List<City>>

    @Delete
    suspend fun deleteCity(city: City)
}