package ooh.app.weatherapp.data.repository

import ooh.app.weatherapp.data.api.ApiService
import ooh.app.weatherapp.domain.model.CurrentWeather
import ooh.app.weatherapp.domain.model.ForecastWeather
import ooh.app.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherImpl @Inject constructor(private val api: ApiService) : WeatherRepository {
    override suspend fun getCurrentWeather(
        city: String,
        aqi: String
    ): CurrentWeather {
        return api.currentWeather(city, aqi)
    }

    override suspend fun getForecastWeather(
        city: String,
        aqi: String,
        days: Int,
        hour: Int
    ): ForecastWeather {
        return api.forecastWeather(city, aqi, days, hour)
    }

}