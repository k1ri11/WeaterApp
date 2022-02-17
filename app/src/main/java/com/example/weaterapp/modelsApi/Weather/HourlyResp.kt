package com.example.weaterapp.modelsApi.Weather

import com.example.weaterapp.models.Hourly
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

data class HourlyResp(
    @SerializedName("dt")
    val date: Int,
    val temp: Double,
    @SerializedName("weather")
    val weatherRespIcon: List<WeatherResp>
){
    fun toHourly(): Hourly {
        val formatter = SimpleDateFormat("HH:mm", Locale.ROOT)
        val dateString: String = formatter.format(Date(date * 1000L))
        return Hourly(
            date = dateString,
            temp = temp.roundToInt().toString().plus(Typography.degree),
            weatherIcon = "https://openweathermap.org/img/wn/${weatherRespIcon[0].icon}.png"
        )
    }
}