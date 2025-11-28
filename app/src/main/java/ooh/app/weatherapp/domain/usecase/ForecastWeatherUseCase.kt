package ooh.app.weatherapp.domain.usecase

import jakarta.inject.Inject
import ooh.app.weatherapp.domain.model.ForecastWeather
import ooh.app.weatherapp.domain.repository.WeatherRepository

class ForecastWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend fun getForecastWeather(
        city: String,
        aqi: String,
        days: Int,
        hour: Int
    ): ForecastWeather {
        return weatherRepository.getForecastWeather(city, aqi, days, hour)
    }
}