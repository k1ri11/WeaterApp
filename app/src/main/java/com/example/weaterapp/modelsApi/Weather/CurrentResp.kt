package com.example.weaterapp.modelsApi.Weather

import com.example.weaterapp.models.Current
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.nextUp
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.text.Typography.degree

data class CurrentResp(
    @SerializedName("dt")
    val date: Int,
    val temp: Double,
    @SerializedName("weather")
    val weatherRespIcon: List<WeatherResp>
){
    fun toCurrent(): Current {
        val formatter = SimpleDateFormat("HH:mm", Locale.ROOT)
        val dateString: String = formatter.format(Date(date * 1000L))
        return Current(
            date = dateString,
            temp = temp.roundToInt().toString().plus(degree),
            weatherIcon = "https://openweathermap.org/img/wn/${weatherRespIcon[0].icon}.png"
        )
    }
}