package com.example.weaterapp.api

import com.example.weaterapp.modelsApi.Search.SearchResult
import com.example.weaterapp.modelsApi.Weather.WeatherModelResp
import com.example.weaterapp.utils.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/onecall?")
    suspend fun getWeatherModel(
        @Query("lat")
        lat: Double,
        @Query("lon")
        lon: Double,
        @Query("exclude")
        exclude: String = "minutely,alerts",
        @Query("units")
        units: String = "metric",
        @Query("appid")
        appid: String = API_KEY
    ): WeatherModelResp

    @GET("geo/1.0/direct?")
    suspend fun searchLocationByName(
        @Query("q")
        query: String,
        @Query("limit")
        limit: Int = 5,
        @Query("appid")
        appid: String = API_KEY
    ) : SearchResult
}
