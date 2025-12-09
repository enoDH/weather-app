package ooh.app.weatherapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ooh.app.weatherapp.domain.model.CurrentWeather
import ooh.app.weatherapp.domain.model.ForecastWeather
import ooh.app.weatherapp.domain.model.LastForecast
import ooh.app.weatherapp.domain.model.LastWeather

interface WeatherRepository {
    suspend fun getCurrentWeather(city: String, aqi: String): CurrentWeather
    suspend fun getForecastWeather(city: String, aqi: String, days: Int, hour: Int): ForecastWeather
    suspend fun getLastWeather(cityId: Int): LastWeather
    suspend fun getLastForecast(cityId: Int): List<LastForecast>
    suspend fun insertWeather(weather: LastWeather)
    suspend fun insertForecast(forecast: List<LastForecast>)
    suspend fun insertCity(city: String)
    suspend fun getCities(): List<String>
    suspend fun saveCity(city: String)
    fun getCity(): Flow<String?>

    suspend fun getCityIdByCityName(cityName: String):Int
}