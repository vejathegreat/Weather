package com.velaphi.weather.api

import com.velaphi.weather.model.ForecastResponse
import com.velaphi.weather.model.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    companion object{
        const val LONGITUDE = "lon"
        const val LATITUDE = "lat"
        const val UNITS = "units"
    }

    @GET("weather?")
    fun getWeatherByGPS(@Query(LATITUDE) latitude: String, @Query(LONGITUDE) longitude: String, @Query(
        UNITS) units: String): Single<WeatherResponse>

    @GET("forecast?")
    fun getForecastByGPS(@Query(LATITUDE) latitude: String, @Query(LONGITUDE) longitude: String, @Query(
        UNITS) units: String): Single<ForecastResponse>
}