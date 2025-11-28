package ooh.app.weatherapp.domain.repository

import ooh.app.weatherapp.domain.model.CurrentWeather
import ooh.app.weatherapp.domain.model.ForecastWeather

interface WeatherRepository {
    suspend fun getCurrentWeather(city: String, aqi: String): CurrentWeather
    suspend fun getForecastWeather(city: String, aqi: String, days: Int, hour: Int): ForecastWeather
}