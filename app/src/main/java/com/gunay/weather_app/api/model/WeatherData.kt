package com.gunay.weather_app.api.model

import com.google.gson.annotations.SerializedName

data class WeatherData (
    val name: String,
    val main: Main,
    val weather: List<Weather>
)