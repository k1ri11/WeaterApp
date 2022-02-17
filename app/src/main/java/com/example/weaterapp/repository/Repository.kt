package com.example.weaterapp.repository

import com.example.weaterapp.api.RetrofitInstance
import com.example.weaterapp.modelsApi.Search.SearchResult
import com.example.weaterapp.modelsApi.Weather.WeatherModelResp

class Repository {
    suspend fun getWeatherModel(lat: Double, lon: Double): WeatherModelResp {
        return RetrofitInstance.api.getWeatherModel(lat = lat, lon = lon)
    }

    suspend fun searchLocationByName(query: String): SearchResult{
        return RetrofitInstance.api.searchLocationByName(query = query)
    }
}