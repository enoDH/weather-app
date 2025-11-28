package ooh.app.weatherapp.data.api

import ooh.app.weatherapp.domain.model.CurrentWeather
import ooh.app.weatherapp.domain.model.ForecastWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current.json")
    suspend fun currentWeather(
        @Query("q") city: String,
        @Query("aqi") aqi: String
    ): CurrentWeather

    @GET("forecast.json")
    suspend fun forecastWeather(
        @Query("q") city: String,
        @Query("aqi") aqi: String,
        @Query("days") days: Int,
        @Query("hour") hour: Int,
    ): ForecastWeather
}