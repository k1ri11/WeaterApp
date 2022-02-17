package com.example.weaterapp.modelsApi.Weather

import com.example.weaterapp.models.WeatherModel
import com.google.gson.annotations.SerializedName

data class WeatherModelResp(
    @SerializedName("current")
    val currentResp: CurrentResp,
    @SerializedName("daily")
    val dailyResp: List<DailyResp>,
    @SerializedName("hourly")
    val hourlyResp: List<HourlyResp>,
    val lat: Double,
    val lon: Double,
){
    fun toWeatherModel(): WeatherModel{
        val hourlyRespList = hourlyResp.subList(0,24)
        return WeatherModel(
            current = currentResp.toCurrent(),
            daily = dailyResp.map { it.toDaily() },
            hourly = hourlyRespList.map { it.toHourly() },
            lat = lat,
            lon = lon
        )
    }

}