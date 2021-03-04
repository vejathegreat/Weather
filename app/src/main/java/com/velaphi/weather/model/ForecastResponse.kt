package com.velaphi.weather.model
import com.google.gson.annotations.SerializedName


class ForecastResponse{
     @SerializedName("list")
     var list: List<Forecast>? = null

     class Forecast {
         @SerializedName("wind")
         var wind: Wind? = null
         @SerializedName("weather")
         var weather: List<Weather>? = null
         @SerializedName("main")
         var main: Main? = null
         @SerializedName("dt")
         var dt = 0
     }

     class Wind {
         @SerializedName("speed")
         var speed = 0.0
     }

     class Weather {
         @SerializedName("icon")
         var icon: String? = null
         @SerializedName("main")
         var main: String? = null
         @SerializedName("id")
         var id = 0

     }

     class Main {
         @SerializedName("humidity")
         var humidity = 0
         @SerializedName("pressure")
         var pressure = 0
         @SerializedName("feels_like")
         var feelsLike = 0.0
         @SerializedName("temp")
         var temp = 0.0

     }
 }