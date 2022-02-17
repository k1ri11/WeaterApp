package com.example.weaterapp.models

data class WeatherModel(
    var location: String = "",
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
)