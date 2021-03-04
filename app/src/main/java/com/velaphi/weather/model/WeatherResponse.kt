package com.velaphi.weather.model

import com.google.gson.annotations.SerializedName

class WeatherResponse {
    @SerializedName("name")
    var name: String? = null
    @SerializedName("sys")
    var sys: Sys? = null
    @SerializedName("wind")
    var wind: Wind? = null
    @SerializedName("visibility")
    var visibility = 0
    @SerializedName("main")
    var main: Main? = null
    @SerializedName("weather")
    var weather: List<Weather>? = null

    class Sys {
        @SerializedName("sunset")
        var sunset = 0
        @SerializedName("sunrise")
        var sunrise = 0
        @SerializedName("country")
        var country: String? = null
        @SerializedName("id")
        var ıd = 0
        @SerializedName("type")
        var type = 0

    }

    class Wind {
        @SerializedName("speed")
        var speed = 0.0
    }

    class Main {
        @SerializedName("humidity")
        var humidity = 0
        @SerializedName("pressure")
        var pressure = 0
        @SerializedName("temp")
        var temp = 0.0

    }

    class Weather {
        @SerializedName("icon")
        var icon: String? = null
        @SerializedName("description")
        var description: String? = null
        @SerializedName("main")
        var main: String? = null

    }
}