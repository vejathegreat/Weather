package com.velaphi.weather.api

import com.velaphi.weather.model.ForecastResponse
import com.velaphi.weather.model.WeatherResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPIClient {

    companion object{
        const val  BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(getOkHttpClient())
        .build()
        .create(WeatherAPI::class.java)

    fun getDataFromGps(
        latitude: String,
        longitude: String,
        units: String
    ): Single<WeatherResponse> {
        return api.getWeatherByGPS(latitude, longitude, units)
    }

    fun getForecastFromGps(
        latitude: String,
        longitude: String,
        units: String
    ): Single<ForecastResponse> {
        return api.getForecastByGPS(latitude, longitude, units)
    }

    private fun getOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(RequestInterceptor())
        return client.build()
    }

}