package com.example.weaterapp.modelsApi.Weather

import com.example.weaterapp.models.Daily
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

data class DailyResp(
    @SerializedName("dt")
    val date: Int,
    @SerializedName("temp")
    val tempResp: TempResp,
    @SerializedName("weather")
    val weatherRespIcon: List<WeatherResp>
){
    fun toDaily(): Daily {
        val formatter = SimpleDateFormat("E, dd MMM", Locale.getDefault())
        val dateString: String = formatter.format(Date(date * 1000L))
        return Daily(
            date = dateString,
            tempDay = tempResp.day.roundToInt().toString().plus(Typography.degree),
            tempNight = tempResp.night.roundToInt().toString().plus(Typography.degree),
            weatherIcon = "https://openweathermap.org/img/wn/${weatherRespIcon[0].icon}.png"
        )
    }
}