package com.example.weaterapp.repository

import androidx.lifecycle.LiveData
import com.example.weaterapp.api.RetrofitInstance
import com.example.weaterapp.database.CityDao
import com.example.weaterapp.models.City
import com.example.weaterapp.modelsApi.Search.SearchResult
import com.example.weaterapp.modelsApi.Weather.WeatherModelResp

class Repository(private val cityDao: CityDao) {

    val allCities: LiveData<List<City>> = cityDao.getAllCities()

    suspend fun getWeatherModel(lat: Double, lon: Double): WeatherModelResp {
        return RetrofitInstance.api.getWeatherModel(lat, lon)
    }

    suspend fun searchLocationByName(query: String): SearchResult {
        return RetrofitInstance.api.searchLocationByName(query)
    }

    suspend fun addCity(city: City) {
        cityDao.addCity(city)
    }

    suspend fun deleteCity(city: City) {
        cityDao.deleteCity(city)
    }
}